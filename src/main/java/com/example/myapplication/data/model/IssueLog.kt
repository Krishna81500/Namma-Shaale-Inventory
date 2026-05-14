package com.example.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "issue_logs",
    foreignKeys = [
        ForeignKey(
            entity = Asset::class,
            parentColumns = ["assetId"],
            childColumns = ["assetId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class IssueLog(
    @PrimaryKey(autoGenerate = true)
    val issueId: Long = 0,
    val assetId: Long,
    val description: String,
    val severity: String, // Low, Medium, High, Critical
    val dateReported: String,
    val repairStatus: String, // Pending, In Progress, Resolved
    val photoUri: String? = null
)
