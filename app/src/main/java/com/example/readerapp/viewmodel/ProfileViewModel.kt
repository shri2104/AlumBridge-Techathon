package com.example.readerapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readerapp.ProfileData.Profile
import com.example.readerapp.ProfileData.ProfileDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val dao: ProfileDao) : ViewModel() {

    private val _profile = MutableStateFlow<Profile?>(null)
    val profile: StateFlow<Profile?> = _profile

    init {
        fetchProfile()
    }

    private fun fetchProfile() {
        viewModelScope.launch {
            val fetchedProfile = dao.getProfile()
            _profile.value = fetchedProfile
        }
    }

    fun saveProfile(name: String, email: String, year: String, job: String) {
        viewModelScope.launch {
            val currentProfile = _profile.value
            if (currentProfile == null) {
                // Insert new profile
                dao.insertProfile(Profile(name = name, email = email, year = year, job = job))
            } else {
                // Update existing profile
                val updatedProfile = currentProfile.copy(
                    name = name,
                    email = email,
                    year = year,
                    job = job
                )
                dao.updateProfile(updatedProfile)
            }
            fetchProfile()
        }
    }
}