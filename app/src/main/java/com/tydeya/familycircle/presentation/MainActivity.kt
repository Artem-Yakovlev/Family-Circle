package com.tydeya.familycircle.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.theartofdev.edmodo.cropper.CropImage
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.authentication.accountsync.AccountExistingCheckUpCallback
import com.tydeya.familycircle.data.authentication.accountsync.AccountSyncTool
import com.tydeya.familycircle.data.constants.Application.*
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor
import com.tydeya.familycircle.domain.kitchenorganizer.notifications.KitchenOrganizerShelfLifeReceiver
import com.tydeya.familycircle.domain.messenger.interactor.abstraction.MessengerInteractorCallback
import com.tydeya.familycircle.domain.messenger.interactor.details.MessengerInteractor
import com.tydeya.familycircle.presentation.ui.registrationpart.FirstStartActivity
import com.tydeya.familycircle.presentation.viewmodel.CroppedImageViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MessengerInteractorCallback, AccountExistingCheckUpCallback {

    private var currentNavController: LiveData<NavController>? = null

    @Inject
    lateinit var familyInteractor: FamilyInteractor

    @Inject
    lateinit var messengerInteractor: MessengerInteractor

    private var isSavedInstanceNull = false
    private var isEntrySuccessful = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        splashStubVisibility(true)
        verificationCheck()
        KitchenOrganizerShelfLifeReceiver.initAlarm(this)
        if (savedInstanceState == null) {
            isSavedInstanceNull = true
        }
    }

    /**
     * Bottom navigation
     * */

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val navGraphIds = listOf(R.navigation.live, R.navigation.plan, R.navigation.correspondence,
                R.navigation.map, R.navigation.manager_menu)

        currentNavController = main_bottom_navigation_view.setupWithNavController(
                navGraphIds = navGraphIds,
                fragmentManager = supportFragmentManager,
                containerId = R.id.nav_host_container,
                intent = intent
        )
    }

    /**
     * SPLASH
     * */

    private fun splashStubVisibility(isSplashVisible: Boolean) {
        splash_image.visibility = if (isSplashVisible) View.VISIBLE else View.INVISIBLE

        val interfaceVisibility = if (isSplashVisible) View.INVISIBLE else View.VISIBLE
        main_bottom_navigation_view.visibility = interfaceVisibility
        nav_host_container.visibility = interfaceVisibility
    }

    /**
     * Verification
     * */

    private fun verificationCheck() {
        FirebaseAuth.getInstance().currentUser?.phoneNumber?.let {
            AccountSyncTool(this).isAccountWithPhoneExist(it)
            return
        }
        startRegistration(REGISTRATION_FULL)
    }

    private fun startRegistration(tag: String) {
        startActivity(Intent(this, FirstStartActivity::class.java).apply {
            putExtra(REGISTRATION_MODE, tag)
        })
        finish()
    }

    override fun accountIsNotExist() {
        startRegistration(REGISTRATION_ONLY_ACCOUNT_CREATION)
    }

    override fun accountIsExist(userId: String, families: List<String>) {

        val preferences = getSharedPreferences(SHARED_PREFERENCE_USER_SETTINGS, Context.MODE_PRIVATE)
        val currentFamily = preferences.getString(CURRENT_FAMILY_ID, "")

        if (currentFamily in families) {
            App.getComponent().injectActivity(this)
            if (isSavedInstanceNull) {
                isEntrySuccessful = true
                setupBottomNavigationBar()
                messengerInteractor.subscribe(this)
            }
            splashStubVisibility(false)
        } else {
            getSharedPreferences(SHARED_PREFERENCE_USER_SETTINGS, Context.MODE_PRIVATE)
                    .edit().putString(CURRENT_FAMILY_ID, "").apply()
            startRegistration(REGISTRATION_ONLY_FAMILY_SELECTION)
        }
    }

    /**
     * Bottom navigation badges
     * */
    override fun messengerDataFromServerUpdated() {
        updateBadges()
    }

    private fun updateBadges() {
        if (messengerInteractor.numberOfUnreadMessages == 0) {
            main_bottom_navigation_view.removeBadge(R.id.correspondence)
        } else {
            main_bottom_navigation_view.getOrCreateBadge(R.id.correspondence)
                    .backgroundColor = ContextCompat.getColor(this, R.color.colorConversationBadge)
            main_bottom_navigation_view.getOrCreateBadge(R.id.correspondence)
                    .number = messengerInteractor.numberOfUnreadMessages
        }
    }

    /**
     * Image cropper results
     * */

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val croppedImageViewModel = ViewModelProvider(this)
                    .get(CroppedImageViewModel::class.java)

            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                croppedImageViewModel.imageCroppedSuccessfully(result)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                croppedImageViewModel.imageCroppedWithError(result)
            }
        }
    }

    /**
     * Interaction callbacks
     * */

    override fun onResume() {
        super.onResume()
        if (isEntrySuccessful) {
            messengerInteractor.subscribe(this)
        }
    }

    override fun onPause() {
        super.onPause()
        if (isEntrySuccessful) {
            messengerInteractor.unsubscribe(this)
        }
    }

    /**
     * Main application activity
     * */

    fun setBottomNavigationVisibility(isVisible: Boolean) {
        main_bottom_navigation_view.visibility = if (isVisible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

}
