package com.example.postacos.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserEntity>

    @Query("SELECT * FROM users WHERE uid = :uid")
    suspend fun getUserByUid(uid: String): UserEntity?

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateUser(user: UserEntity)

    @Delete(UserEntity::class)
    suspend fun deleteUser(user: UserEntity)


}