package com.example.readerapp.donationdata

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.readerapp.Donationdata2.TotalDonation
import com.example.readerapp.Donationdata2.TotalDonationDao

@Database(entities = [TotalDonation::class], version = 1)
abstract class TotalDonationDatabase : RoomDatabase() {
    abstract fun totalDonationDao(): TotalDonationDao
}
