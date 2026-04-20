package com.example.postacos.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("users")
data class UserEntity(
    @PrimaryKey val uid: String,
    val name: String,
    val role: String,
    val email: String
)
