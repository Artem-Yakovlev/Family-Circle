package com.tydeya.familycircle.presentation.ui.registrationpart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.theartofdev.edmodo.cropper.CropImage
import com.tydeya.familycircle.R
import com.tydeya.familycircle.data.constants.Application.*
import com.tydeya.familycircle.framework.datepickerdialog.ImageCropperUsable

class FirstStartActivity : AppCompatActivity(R.layout.activity_first_start) {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = Navigation.findNavController(this, R.id.first_start_activity_container)
        navigateToDestinationByMode(REGISTRATION_ONLY_FAMILY_SELECTION)
    }

    private fun navigateToDestinationByMode(mode: String) {
        when (mode) {
            REGISTRATION_ONLY_ACCOUNT_CREATION -> {
                navController.navigate(R.id.createNewAccountFragment)
            }
            REGISTRATION_ONLY_FAMILY_SELECTION -> {
                navController.navigate(R.id.selectFamilyFragment)
            }
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            if (navController.currentDestination?.id == R.id.createNewAccountFragment) {

                val navHostFragment = (supportFragmentManager
                        .findFragmentById(R.id.first_start_activity_container) as NavHostFragment?)!!

                val imageCropperUsable = (navHostFragment
                        .childFragmentManager.fragments[0] as ImageCropperUsable)

                val result = CropImage.getActivityResult(data)

                if (resultCode == Activity.RESULT_OK) {
                    imageCropperUsable.imageCroppedSuccessfully(result)
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    imageCropperUsable.imageCroppedWithError(result)
                }

            }
        }

    }
}