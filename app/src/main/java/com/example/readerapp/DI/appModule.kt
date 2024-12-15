package com.example.readerapp.DI

import android.content.Context
import androidx.room.Room
import com.example.readerapp.jobData.JobDao
import com.example.readerapp.jobData.JobDatabase
import com.example.readerapp.jobData.jobRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context = context
    @Singleton
    @Provides
    fun provideDatabase(context: Context): JobDatabase =
        Room.databaseBuilder(context, JobDatabase::class.java, "job_database").build()
    @Singleton
    @Provides
    fun provideJobDao(database: JobDatabase): JobDao = database.jobDao()
    @Singleton
    @Provides
    fun provideJobRepo(jobDao: JobDao): jobRepo = jobRepo(jobDao)
}
