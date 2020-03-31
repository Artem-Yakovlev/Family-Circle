package com.tydeya.familycircle.framework.datepickerdialog;

import com.theartofdev.edmodo.cropper.CropImage;

public interface ImageCropperUsable {

    void imageCroppedSuccessfully(CropImage.ActivityResult activityResult);

    void imageCroppedWithError(CropImage.ActivityResult activityResult);
}
