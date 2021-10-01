package com.sigmasoftware.akucherenko.todolist.model

import java.util.*

data class Item(
    val contentTask: String,
    var done: Boolean,
    val dateCreated: Date
)
