package com.example.readerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readerapp.jobData.JobPostin

import com.example.readerapp.jobData.jobRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobViewModel @Inject constructor(private val repo: jobRepo) : ViewModel() {

    private val _jobList = MutableStateFlow<List<JobPostin>>(emptyList())
    val jobList: StateFlow<List<JobPostin>> get() = _jobList
    val totalJobCount: StateFlow<Int> = _jobList.map { it.size }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        0
    )
    init {
        fetchJobs()
    }

    fun addJob(job: JobPostin) {
        viewModelScope.launch { repo.insertJob(job) }
    }

    private fun fetchJobs() {
        viewModelScope.launch {
            repo.getAllJobs().collect { jobs -> _jobList.value = jobs }
        }
    }
}
