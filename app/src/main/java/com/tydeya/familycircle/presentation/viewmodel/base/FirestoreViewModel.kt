package com.tydeya.familycircle.presentation.viewmodel.base

import androidx.lifecycle.ViewModel
import com.tydeya.familycircle.domain.familyinteraction.serverInteractionDetect

abstract class FirestoreViewModel : ViewModel() {
    init {
        serverInteractionDetect()
    }
}