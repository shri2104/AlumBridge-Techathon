package com.example.readerapp.jobData

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "jobList")
data class JobPostin(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val company: String,
    val role: String,
    val description: String,
    val applyLink: String
)

@Dao
interface JobDao {
    @Insert
    suspend fun insertJob(job: JobPostin)

    @Query("SELECT * FROM jobList")
    fun getAllJobs(): Flow<List<JobPostin>>
}
