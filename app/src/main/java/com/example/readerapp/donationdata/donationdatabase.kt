package com.example.readerapp.donationdata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DonationDao {
    @Query("SELECT * FROM donation_table")
    suspend fun getDonation(): Donation?
    @Insert
    suspend fun insertDonation(donation: Donation)
    @Update
    suspend fun updateDonation(donation: Donation)
}
