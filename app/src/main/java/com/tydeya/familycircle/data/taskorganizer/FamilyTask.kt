package com.tydeya.familycircle.data.taskorganizer

data class FamilyTask(
        val id: String,
        val author: String,
        val workers: ArrayList<String>,
        val title: String,
        val text: String,
        val status: TaskStatus
)
