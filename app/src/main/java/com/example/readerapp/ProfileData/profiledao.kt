package com.example.readerapp.ProfileData

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update

@Entity(tableName = "profile_table")
data class Profile(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val year: String,
    val job: String
)

@Dao
interface ProfileDao {

    @Query("SELECT * FROM profile_table")
    suspend fun getProfile(): Profile?

    @Insert
    suspend fun insertProfile(profile: Profile)

    @Update
    suspend fun updateProfile(profile: Profile)
}