package com.example.readerapp.Donationdata2

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "total_donation")
data class TotalDonation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val donorName: String = "",
    val Batchof: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface TotalDonationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTotalDonation(totalDonation: TotalDonation)

    @Query("SELECT * FROM total_donation")
    fun getAllTotalDonations(): Flow<List<TotalDonation>>
}
