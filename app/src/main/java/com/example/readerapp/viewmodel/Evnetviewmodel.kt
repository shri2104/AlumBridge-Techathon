package com.example.readerapp.viewmodel

import com.example.readerapp.eventdata.Event
import com.example.readerapp.eventdata.EventDao
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(private val dao: EventDao) : ViewModel() {
    private val _events = MutableStateFlow<List<Event>>(emptyList())
    val events: StateFlow<List<Event>> = _events
    init {
        fetchEvents()
    }
    private fun fetchEvents() {
        viewModelScope.launch {
            val fetchedEvents = dao.getEvents()
            _events.value = fetchedEvents
        }
    }
    fun saveEvent(
        eventHeadline: String,
        eventDescription: String,
        eventDates: String,
        eventLocation: String,
        eventType: String
    ) {
        viewModelScope.launch {
            val newEvent = Event(
                eventHeadline = eventHeadline,
                eventDescription = eventDescription,
                eventDates = eventDates,
                eventLocation = eventLocation,
                eventType = eventType
            )
            dao.insertEvent(newEvent)
            fetchEvents()
        }
    }
    fun updateEvent(event: Event) {
        viewModelScope.launch {
            dao.updateEvent(event)
            fetchEvents()
        }
    }
}

