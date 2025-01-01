package com.example.readerapp.Donationdata3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readerapp.Donationdata3.StudentDonation
import com.example.readerapp.Donationdata3.StudentDonationDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentDonationViewModel @Inject constructor(
    private val studentDonationDao: StudentDonationDao
) : ViewModel() {

    fun addStudentDonation(amount: Double, donorName: String = "", batch: Int, referenceId: String) {
        viewModelScope.launch {
            val studentDonation = StudentDonation(
                amount = amount,
                donorName = donorName,
                batch = batch,
                referenceId = referenceId // passing reference ID
            )
            studentDonationDao.insertStudentDonation(studentDonation)
        }
    }

    val allStudentDonations = studentDonationDao.getAllStudentDonations()
}
