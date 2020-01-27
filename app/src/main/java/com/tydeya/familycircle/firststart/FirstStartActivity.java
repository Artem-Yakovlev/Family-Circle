package com.tydeya.familycircle.firststart;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.theartofdev.edmodo.cropper.CropImage;
import com.tydeya.familycircle.R;
import com.tydeya.familycircle.commonhandlers.DatePickerDialog.ImageCropperUsable;

public class FirstStartActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);

        navController = Navigation.findNavController(this,
                R.id.first_start_activity_main_navigation);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            assert navController.getCurrentDestination() != null;

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (navController.getCurrentDestination().getId() == R.id.createNewAccountFragment) {

                ImageCropperUsable imageCropperUsable = (ImageCropperUsable) getSupportFragmentManager()
                        .findFragmentById(R.id.createNewAccountFragment);

                assert imageCropperUsable != null;

                if (resultCode == RESULT_OK) {

                    imageCropperUsable.imageCroppedSuccessfully(result);

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                    imageCropperUsable.imageCroppedWithError(result);

                }
            }
        }
    }
}
