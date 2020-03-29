package com.tydeya.familycircle.data.taskorganizer

data class Task(val id: String, val author: String, val worker: String, var text: String,
                var status: TaskStatus)