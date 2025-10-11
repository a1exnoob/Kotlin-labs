package com.example.appyaz.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "uaz_models")
data class UazEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val cost: String,
    val description: String
)


