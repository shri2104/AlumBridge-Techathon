import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.readerapp.Navigation.ReaderScreens
import com.example.readerapp.R
import com.example.readerapp.viewmodel.EventViewModel

@Composable
fun EventReunionsScreen(eventViewModel: EventViewModel, navController: NavHostController) {
    val context = LocalContext.current
    var headline by remember { mutableStateOf(TextFieldValue()) }
    var description by remember { mutableStateOf(TextFieldValue()) }
    var dates by remember { mutableStateOf(TextFieldValue()) }
    var location by remember { mutableStateOf(TextFieldValue()) }
    var eventType by remember { mutableStateOf("Select Event Type") }
    var expanded by remember { mutableStateOf(false) }
    val isFormValid = headline.text.isNotEmpty() && description.text.isNotEmpty() &&
            dates.text.isNotEmpty() && location.text.isNotEmpty() &&
            eventType != "Select Event Type"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Events & Reunions") },
                backgroundColor = Color(0xFF6200EE),
                contentColor = Color.White
            )
        },
        content = { contentPadding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)) {
                Image(
                    painter = painterResource(id = R.drawable.jason_leung_xaanw0s0pmk_unsplash),
                    contentDescription = "Background Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    OutlinedTextField(
                        value = headline,
                        onValueChange = { headline = it },
                        label = { Text("Event Headline") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF6200EE),
                            cursorColor = Color(0xFF6200EE)
                        )
                    )
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Event Description") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF6200EE),
                            cursorColor = Color(0xFF6200EE)
                        )
                    )
                    OutlinedTextField(
                        value = dates,
                        onValueChange = { dates = it },
                        label = { Text("Event Dates") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF6200EE),
                            cursorColor = Color(0xFF6200EE)
                        )
                    )
                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        label = { Text("Event Location") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF6200EE),
                            cursorColor = Color(0xFF6200EE)
                        )
                    )
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(
                            onClick = { expanded = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = eventType)
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(onClick = {
                                eventType = "Webinar"
                                expanded = false
                            }) {
                                Text("Webinar")
                            }
                            DropdownMenuItem(onClick = {
                                eventType = "Reunion"
                                expanded = false
                            }) {
                                Text("Reunion")
                            }
                            DropdownMenuItem(onClick = {
                                eventType = "Workshop"
                                expanded = false
                            }) {
                                Text("Workshop")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp)) // Space before the buttons

                    Button(
                        onClick = {
                            eventViewModel.saveEvent(
                                eventHeadline = headline.text,
                                eventDescription = description.text,
                                eventDates = dates.text,
                                eventLocation = location.text,
                                eventType = eventType
                            )
                            Toast.makeText(context, "Event Posted", Toast.LENGTH_SHORT).show()
                            navController.navigate(ReaderScreens.postedEvents.name)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EE)),
                        enabled = isFormValid
                    ) {
                        Text(text = "POST", color = Color.White, fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            navController.navigate(ReaderScreens.postedEvents.name)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EE)),
                    ) {
                        Text(text = "POSTED EVENTS", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
    )
}
