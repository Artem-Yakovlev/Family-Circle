package com.tydeya.familycircle.data.taskorganizer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FamilyTask(
        val id: String = "",
        val author: String,
        val workers: ArrayList<String>,
        val title: String,
        val text: String,
        val status: TaskStatus
) : Parcelable
