package com.example.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "members")
data class Member(
    @PrimaryKey(autoGenerate = true)
    val memberId: Long = 0,
    val name: String,
    val role: String,
    val status: String, // Present, Absent, On Leave
    val bio: String,
    val responsibilities: String, // Comma-separated
    val imageUrl: String? = null
)
