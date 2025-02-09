package com.example.readerapp.Screen.EventsandReunion

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import com.example.readerapp.Retrofit.ApiService
import com.example.readerapp.Retrofit.EventData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(apiService: ApiService) {
    var eventList by remember { mutableStateOf<List<EventData>?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val response = apiService.getAllEvents()
                eventList = response
            } catch (e: Exception) {
                eventList = emptyList()
            } finally {
                isLoading = false
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Events & Reunions") }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when {
                isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                eventList.isNullOrEmpty() -> Text(
                    text = "No events found",
                    modifier = Modifier.align(Alignment.Center)
                )
                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                ) {
                    items(eventList!!) { event ->
                        EventItem(event)
                    }
                }
            }
        }
    }
}

@Composable
fun EventItem(event: EventData) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            event.Headline?.let { Text(text = it, style = MaterialTheme.typography.headlineSmall) }
            Text(text = "Date: ${event.Dates}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Location: ${event.Location}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Type: ${event.EventType}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
