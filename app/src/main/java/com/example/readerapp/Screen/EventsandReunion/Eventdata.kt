package com.example.readerapp.Screen.EventsandReunion

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.readerapp.Retrofit.ApiService
import com.example.readerapp.Retrofit.EventData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDataForm(navController: NavController, apiService: ApiService, userId: String) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var headline by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var dates by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var forms by remember { mutableStateOf("") }
    var eventType by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Event and Reunion", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Text(
                    text = "Powered by Alumni Network",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputFields("Enter Headline") { headline = it }
            InputFields("Enter Description") { description = it }
            InputFields("Enter Dates") { dates = it }
            InputFields("Enter Location") { location = it }
            InputFields("Fill the form") { forms = it }
            InputFields("Event Type") { eventType = it }
            Button(
                onClick = {
                    val eventData = EventData(
                        userId = userId,
                        Headline = headline,
                        Description = description,
                        Dates = dates,
                        Location = location,
                        Forms = forms,
                        EventType = eventType,
                        createdAt = System.currentTimeMillis() // Add current timestamp as createdAt
                    )
                    coroutineScope.launch(Dispatchers.IO) {
                        try {
                            val response = apiService.StoreEventData(eventData)
                            // Handle the response if needed
                        } catch (e: Exception) {
                            // Handle error
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }
        }
    }
}

@Composable
fun InputFields(placeholder: String, onValueChange: (String) -> Unit) {
    var inputData by remember { mutableStateOf("") }
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        TextField(
            value = inputData,
            onValueChange = {
                inputData = it
                onValueChange(it)
            },
            placeholder = { Text(text = placeholder) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Blue,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewEventDataForm() {
//    EventDataForm()
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkingHours() {
    var expanded by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf("Reunion") }
    val countries = listOf("Webinar", "Reunion","Workshop")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = selectedCountry,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Dropdown Arrow",
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Blue,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            countries.forEach { country ->
                DropdownMenuItem(
                    text = { Text(country) },
                    onClick = {
                        selectedCountry = country
                        expanded = false
                    }
                )
            }
        }
    }
}