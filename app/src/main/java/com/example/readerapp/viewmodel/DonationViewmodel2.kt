package com.example.readerapp.donationdata

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readerapp.Donationdata2.TotalDonation
import com.example.readerapp.Donationdata2.TotalDonationDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TotalDonationViewModel @Inject constructor(
    private val totalDonationDao: TotalDonationDao
) : ViewModel() {
    fun addTotalDonation(amount: Double, donorName: String = "", batchof: Int) {
        viewModelScope.launch {
            val totalDonation = TotalDonation(
                amount = amount,
                donorName = donorName,
                Batchof = batchof
            )
            totalDonationDao.insertTotalDonation(totalDonation)
        }
    }
    val allTotalDonations = totalDonationDao.getAllTotalDonations()
}
