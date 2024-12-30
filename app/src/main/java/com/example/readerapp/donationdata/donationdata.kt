package com.example.readerapp.donationdata



import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "donation_table")
data class Donation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val accountHolderName: String,
    val bankName: String,
    val accountNumber: String,
    val ifscCode: String
)
