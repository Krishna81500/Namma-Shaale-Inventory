package com.example.myapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.model.Asset
import com.example.myapplication.data.model.ConditionHistory
import com.example.myapplication.data.model.IssueLog

@Database(
    entities = [
        Asset::class, 
        IssueLog::class, 
        ConditionHistory::class, 
        com.example.myapplication.data.model.Member::class, 
        com.example.myapplication.data.model.Attendance::class,
        com.example.myapplication.data.model.User::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun assetDao(): AssetDao
}
