package com.example.readerapp.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.readerapp.donationdata.Donation
import com.example.readerapp.donationdata.DonationDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DonationViewModel @Inject constructor(private val dao: DonationDao) : ViewModel() {

    private val _donation = MutableStateFlow<Donation?>(null)
    val donation: StateFlow<Donation?> = _donation
    init {
        fetchDonation()
    }
    private fun fetchDonation() {
        viewModelScope.launch {
            val fetchedDonation = dao.getDonation()
            _donation.value = fetchedDonation
        }
    }
    fun saveDonation(accountHolderName: String, bankName: String, accountNumber: String, ifscCode: String) {
        viewModelScope.launch {
            val currentDonation = _donation.value
            if (currentDonation == null) {
                dao.insertDonation(Donation(accountHolderName = accountHolderName, bankName = bankName, accountNumber = accountNumber, ifscCode = ifscCode))
            } else {
                val updatedDonation = currentDonation.copy(
                    accountHolderName = accountHolderName,
                    bankName = bankName,
                    accountNumber = accountNumber,
                    ifscCode = ifscCode
                )
                dao.updateDonation(updatedDonation)
            }
            fetchDonation()
        }
    }
}
