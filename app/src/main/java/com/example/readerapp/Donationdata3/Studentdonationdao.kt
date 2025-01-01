package com.example.readerapp.Donationdata3



import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Entity(tableName = "student_donation")
data class StudentDonation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val donorName: String = "",
    val batch: Int,
    val referenceId: String = "",
    val timestamp: Long = System.currentTimeMillis()
)


@Dao
interface StudentDonationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudentDonation(studentDonation: StudentDonation)

    @Query("SELECT * FROM student_donation")
    fun getAllStudentDonations(): Flow<List<StudentDonation>>
}
