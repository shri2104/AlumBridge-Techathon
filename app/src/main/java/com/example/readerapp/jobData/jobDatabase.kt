package com.example.readerapp.jobData

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [JobPosting::class], version = 1)
abstract class JobDatabase : RoomDatabase() {
    abstract fun jobDao(): JobDao

    companion object {
        @Volatile
        private var INSTANCE: JobDatabase? = null

        fun getInstance(context: Context): JobDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    JobDatabase::class.java,
                    "job_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
