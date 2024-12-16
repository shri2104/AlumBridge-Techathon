package com.example.readerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readerapp.jobData.JobPosting
import com.example.readerapp.jobData.jobRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobViewModel @Inject constructor(private val repo: jobRepo) : ViewModel() {

    private val _jobList = MutableStateFlow<List<JobPosting>>(emptyList())
    val jobList: StateFlow<List<JobPosting>> get() = _jobList

    init {
        fetchJobs()
    }

    fun addJob(job: JobPosting) {
        viewModelScope.launch { repo.insertJob(job) }
    }

    private fun fetchJobs() {
        viewModelScope.launch {
            repo.getAllJobs().collect { jobs -> _jobList.value = jobs }
        }
    }
}
