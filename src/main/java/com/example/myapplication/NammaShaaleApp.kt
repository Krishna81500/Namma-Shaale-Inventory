package com.example.myapplication

import android.app.Application
import androidx.room.Room
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.repository.AssetRepository

class NammaShaaleApp : Application() {
    private val database by lazy { 
        Room.databaseBuilder(this, AppDatabase::class.java, "namma_shaale_db")
            .fallbackToDestructiveMigration()
            .build() 
    }
    val repository by lazy { AssetRepository(database.assetDao()) }
}
