package com.example.readerapp.jobData

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class jobRepo @Inject constructor(private val jobDao: JobDao) {
     suspend fun insertJob(job: JobPostin) = jobDao.insertJob(job)
     fun getAllJobs(): Flow<List<JobPostin>> = jobDao.getAllJobs()
}
