package com.example.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val uniqueId: String, // Unique school ID or teacher ID
    val name: String,
    val email: String,
    val passwordHash: String
)
