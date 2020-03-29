package com.tydeya.familycircle.data.taskorganizer

data class FamilyTask(val id: String, val author: String, var worker: String, var text: String,
                      val time: Long, var status: FamilyTaskStatus)