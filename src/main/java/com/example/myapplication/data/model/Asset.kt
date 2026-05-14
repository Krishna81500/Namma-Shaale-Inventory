package com.example.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assets")
data class Asset(
    @PrimaryKey(autoGenerate = true)
    val assetId: Long = 0,
    val name: String,
    val category: String,
    val location: String,
    val quantity: Int,
    val purchaseDate: String,
    val conditionStatus: String, // GREEN, YELLOW, RED
    val photoUri: String? = null
)
