package com.example.postacos.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database([UserEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}