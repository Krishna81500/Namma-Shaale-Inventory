package com.example.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "condition_history",
    foreignKeys = [
        ForeignKey(
            entity = Asset::class,
            parentColumns = ["assetId"],
            childColumns = ["assetId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ConditionHistory(
    @PrimaryKey(autoGenerate = true)
    val historyId: Long = 0,
    val assetId: Long,
    val status: String, // GREEN, YELLOW, RED
    val checkedBy: String,
    val checkDate: String,
    val remarks: String? = null
)
