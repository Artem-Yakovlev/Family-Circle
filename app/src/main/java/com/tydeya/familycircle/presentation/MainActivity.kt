package com.tydeya.familycircle.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.messaging.FirebaseMessaging
import com.theartofdev.edmodo.cropper.CropImage
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.constants.CLOUD_MESSAGING_KITCHEN_TOPIC
import com.tydeya.familycircle.domain.familyinteractor.details.FamilyInteractor
import com.tydeya.familycircle.domain.kitchenorganizer.notifications.KitchenOrganizerShelfLifeReceiver
import com.tydeya.familycircle.domain.messenger.interactor.abstraction.MessengerInteractorCallback
import com.tydeya.familycircle.domain.messenger.interactor.details.MessengerInteractor
import com.tydeya.familycircle.framework.accountsync.abstraction.AccountExistingCheckUpCallback
import com.tydeya.familycircle.framework.accountsync.details.AccountExistingCheckUpImpl
import com.tydeya.familycircle.framework.datepickerdialog.ImageCropperUsable
import com.tydeya.familycircle.presentation.ui.firststartpage.FirstStartActivity
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


        FirebaseMessaging.getInstance().subscribeToTopic(CLOUD_MESSAGING_KITCHEN_TOPIC)
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

        val navGraphIds = listOf(R.navigation.live, R.navigation.plan,
                R.navigation.correspondence, R.navigation.map, R.navigation.manager_menu)

        currentNavController = main_bottom_navigation_view.setupWithNavController(
                navGraphIds = navGraphIds,
                fragmentManager = supportFragmentManager,
                containerId = R.id.nav_host_container,
                intent = intent
        )
    }

    /**
     * Verification
     * */

    private fun verificationCheck(): Boolean {
        if (FirebaseAuth.getInstance().currentUser == null) {
            startRegistration()
            return false
        } else {
            AccountExistingCheckUpImpl(this).isAccountWithPhoneExist(
                    FirebaseAuth.getInstance().currentUser!!.phoneNumber)

        }
        return true
    }

    private fun startRegistration() {
        val intent = Intent(this, FirstStartActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    override fun accountIsNotExist() {
        FirebaseAuth.getInstance().signOut()
        startRegistration()
    }

    override fun accountIsExist(querySnapshot: QuerySnapshot?) {
        App.getComponent().injectActivity(this)
        if (isSavedInstanceNull) {
            isEntrySuccessful = true
            setupBottomNavigationBar()
            messengerInteractor.subscribe(this)
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

            val navController = Navigation.findNavController(this, R.id.nav_host_container)
            val result = CropImage.getActivityResult(data)

            if (navController.currentDestination!!.id == R.id.memberPersonEditFragment) {
                val navHostFragment = (supportFragmentManager.findFragmentById(R.id.nav_host_container)
                        as NavHostFragment?)!!

                val imageCropperUsable = (navHostFragment
                        .childFragmentManager.fragments[0] as ImageCropperUsable)
                if (resultCode == Activity.RESULT_OK) {
                    imageCropperUsable.imageCroppedSuccessfully(result)
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    imageCropperUsable.imageCroppedWithError(result)
                }
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

    public fun setBottomNavigationVisibility(isVisible: Boolean) {
        main_bottom_navigation_view.visibility = if (isVisible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

}
