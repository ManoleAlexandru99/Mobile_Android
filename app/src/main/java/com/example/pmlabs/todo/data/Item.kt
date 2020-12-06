package com.example.pmlabs.todo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "items")
data class Item(
    @PrimaryKey @ColumnInfo(name = "_id") val _id: String,
    @ColumnInfo(name = "text") var text: String,
    @ColumnInfo(name = "an") var an: String,
    @ColumnInfo(name = "ascultari") var ascultari: Int,
) {
    override fun toString(): String = "$text $an $ascultari";
}