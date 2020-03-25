package com.tydeya.familycircle.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.auth.FirebaseAuth
import com.theartofdev.edmodo.cropper.CropImage
import com.tydeya.familycircle.App
import com.tydeya.familycircle.R
import com.tydeya.familycircle.domain.conversationsinteractor.abstraction.ConversationInteractorCallback
import com.tydeya.familycircle.domain.conversationsinteractor.details.ConversationInteractor
import com.tydeya.familycircle.framework.datepickerdialog.ImageCropperUsable
import com.tydeya.familycircle.ui.firststartpage.FirstStartActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ConversationInteractorCallback {

    private var currentNavController: LiveData<NavController>? = null

    private var conversationInteractor: ConversationInteractor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (verificationCheck()) {
            conversationInteractor = App.getComponent().conversationInteractor
            if (savedInstanceState == null) {
                setupBottomNavigationBar()
            }
        }
    }

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

    private fun verificationCheck(): Boolean {
        if (FirebaseAuth.getInstance().currentUser == null) {
            val intent = Intent(this, FirstStartActivity::class.java).apply { }
            startActivity(intent)
            this.finish()
            return false
        }
        return true
    }

    /**
     * Interaction callbacks
     * */

    override fun onResume() {
        super.onResume()
        conversationInteractor!!.subscribe(this)
        updateBadges()
    }

    override fun onPause() {
        super.onPause()
        conversationInteractor!!.unsubscribe(this)
    }

    /**
     * Bottom navigation badges
     * */

    override fun conversationsDataUpdated() {
        updateBadges()
    }

    private fun updateBadges() {

        if (conversationInteractor!!.actualConversationBadges == 0) {

            main_bottom_navigation_view.removeBadge(R.id.correspondence)

        } else {

            main_bottom_navigation_view.getOrCreateBadge(R.id.correspondence)
                    .backgroundColor = resources.getColor(R.color.colorConversationBadge)

            main_bottom_navigation_view.getOrCreateBadge(R.id.correspondence)
                    .number = conversationInteractor!!.actualConversationBadges
        }
    }

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

}
