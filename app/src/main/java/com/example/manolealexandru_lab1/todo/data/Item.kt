package com.example.manolealexandru_lab1.todo.data

import java.util.*

data class Item(
    val id: String,
    var text: String,
    //var publised: Date,
    //var available: Boolean
) {
    override fun toString(): String = "$text"// $available $publised"
}
