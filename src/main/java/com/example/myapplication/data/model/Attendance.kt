package com.example.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey

@Entity(
    tableName = "attendance",
    foreignKeys = [
        ForeignKey(
            entity = Member::class,
            parentColumns = ["memberId"],
            childColumns = ["memberId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Attendance(
    @PrimaryKey(autoGenerate = true)
    val attendanceId: Long = 0,
    val memberId: Long,
    val date: String, // yyyy-MM-dd
    val status: String // Present, Absent, On Leave
)
