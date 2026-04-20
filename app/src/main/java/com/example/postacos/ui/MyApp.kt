package com.example.postacos.ui

import android.app.Application
import androidx.room.Room
import com.example.postacos.data.AppDatabase

class MyApp : Application() {

    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}