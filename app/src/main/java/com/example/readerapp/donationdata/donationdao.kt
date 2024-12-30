package com.example.readerapp.donationdata



import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Donation::class], version = 1, exportSchema = false)
abstract class DonationDatabase : RoomDatabase() {
    abstract fun donationDao(): DonationDao
}
