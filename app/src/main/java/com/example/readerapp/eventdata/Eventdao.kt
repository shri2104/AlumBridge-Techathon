package com.example.readerapp.eventdata



import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update

@Entity(tableName = "event_table")
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val eventHeadline: String,
    val eventDescription: String,
    val eventDates: String,
    val eventLocation: String,
    val eventType: String
)

@Dao
interface EventDao {

    @Query("SELECT * FROM event_table")
    suspend fun getEvents(): List<Event>

    @Insert
    suspend fun insertEvent(event: Event)

    @Update
    suspend fun updateEvent(event: Event)
}
