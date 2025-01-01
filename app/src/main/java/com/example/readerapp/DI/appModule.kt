package com.example.readerapp.DI

import android.content.Context
import androidx.room.Room
import com.example.readerapp.Donationdata2.TotalDonationDao
import com.example.readerapp.Donationdata3.StudentDonationDao
import com.example.readerapp.Donationdata3.StudentDonationDatabase
import com.example.readerapp.ProfileData.ProfileDao
import com.example.readerapp.ProfileData.ProfileDatabase
import com.example.readerapp.donationdata.DonationDao
import com.example.readerapp.donationdata.DonationDatabase
import com.example.readerapp.donationdata.TotalDonationDatabase
import com.example.readerapp.jobData.JobDao
import com.example.readerapp.jobData.JobDatabase
import com.example.readerapp.jobData.jobRepo
import com.example.readerapp.eventdata.EventDao
import com.example.readerapp.eventdata.EventDatabase
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
    fun provideJobDatabase(context: Context): JobDatabase =
        Room.databaseBuilder(context, JobDatabase::class.java, "job_database").build()

    @Singleton
    @Provides
    fun provideJobDao(database: JobDatabase): JobDao = database.jobDao()

    @Singleton
    @Provides
    fun provideJobRepo(jobDao: JobDao): jobRepo = jobRepo(jobDao)

    @Singleton
    @Provides
    fun provideProfileDatabase(context: Context): ProfileDatabase =
        Room.databaseBuilder(context, ProfileDatabase::class.java, "profile_database").build()

    @Singleton
    @Provides
    fun provideProfileDao(profileDatabase: ProfileDatabase): ProfileDao = profileDatabase.profileDao()

    @Singleton
    @Provides
    fun provideDonationDatabase(context: Context): DonationDatabase =
        Room.databaseBuilder(context, DonationDatabase::class.java, "donation_database").build()

    @Singleton
    @Provides
    fun provideDonationDao(donationDatabase: DonationDatabase): DonationDao = donationDatabase.donationDao()

    @Singleton
    @Provides
    fun provideEventDatabase(context: Context): EventDatabase =
        Room.databaseBuilder(context, EventDatabase::class.java, "event_database").build()

    @Singleton
    @Provides
    fun provideEventDao(eventDatabase: EventDatabase): EventDao = eventDatabase.eventDao()

    @Singleton
    @Provides
    fun provideTotalDonationDatabase(context: Context): TotalDonationDatabase =
        Room.databaseBuilder(context, TotalDonationDatabase::class.java, "total_donation_database").build()

    @Singleton
    @Provides
    fun provideTotalDonationDao(totalDonationDatabase: TotalDonationDatabase): TotalDonationDao =
        totalDonationDatabase.totalDonationDao()

    @Singleton
    @Provides
    fun provideStudentDonationDatabase(context: Context): StudentDonationDatabase =
        Room.databaseBuilder(context, StudentDonationDatabase::class.java, "student_donation_database").build()

    @Singleton
    @Provides
    fun provideStudentDonationDao(studentDonationDatabase: StudentDonationDatabase): StudentDonationDao =
        studentDonationDatabase.studentDonationDao()

}
