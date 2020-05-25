package com.tydeya.familycircle.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.theartofdev.edmodo.cropper.CropImage
import com.tydeya.familycircle.framework.datepickerdialog.ImageCropperUsable
import com.tydeya.familycircle.utils.Resource

class CroppedImageViewModel : ViewModel(), ImageCropperUsable {

    val croppedImage: MutableLiveData<Resource<CropImage.ActivityResult>> =
            MutableLiveData(Resource.Loading())

    override fun imageCroppedSuccessfully(activityResult: CropImage.ActivityResult) {
        this.croppedImage.value = Resource.Success(activityResult)
    }

    override fun imageCroppedWithError(activityResult: CropImage.ActivityResult) {
        this.croppedImage.value = Resource.Failure(activityResult.error)
    }

    fun imageProcessed() {
        this.croppedImage.value = Resource.Loading()
    }
}