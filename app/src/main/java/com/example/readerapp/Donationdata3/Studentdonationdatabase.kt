package com.example.readerapp.Donationdata3



import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [StudentDonation::class], version = 1)
abstract class StudentDonationDatabase : RoomDatabase() {
    abstract fun studentDonationDao(): StudentDonationDao
}
