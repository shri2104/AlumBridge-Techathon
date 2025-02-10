//package com.example.readerapp.Screen.EventsandReunion
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.DateRange
//import androidx.compose.material.icons.filled.LocationOn
//import androidx.compose.material.icons.filled.Timeline
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.navigation.NavHostController
//import com.example.readerapp.viewmodel.EventViewModel
//import com.example.readerapp.R
//import com.example.readerapp.Retrofit.EventData
//
//@Composable
//fun PostedEventsScreen(eventViewModel: EventViewModel, navController: NavHostController) {
//    val events by eventViewModel.events.collectAsState()
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Posted Events") },
//                backgroundColor = Color(0xFF6200EE),
//                contentColor = Color.White,
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            )
//        },
//        content = { contentPadding ->
//            Box(modifier = Modifier
//                .fillMaxSize()
//                .padding(contentPadding)) {
//                Image(
//                    painter = painterResource(id = R.drawable.jason_leung_xaanw0s0pmk_unsplash),
//                    contentDescription = "Background Image",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.fillMaxSize()
//                )
//                if (events.isEmpty()) {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .wrapContentSize(Alignment.Center)
//                            .padding(16.dp)
//                    ) {
//                        Text(
//                            text = "No upcoming events",
//                            style = MaterialTheme.typography.h6.copy(
//                                fontWeight = FontWeight.Bold,
//                                fontSize = 20.sp
//                            ),
//                            color = Color.Black,
//                            modifier = Modifier.align(Alignment.Center)
//                        )
//                    }
//                }
//                else {
//                    LazyColumn(modifier = Modifier.fillMaxSize()) {
//                        item {
//                            Text(
//                                text = "Latest Events",
//                                style = MaterialTheme.typography.h6.copy(
//                                    fontWeight = FontWeight.Bold,
//                                    fontSize = 20.sp
//                                ),
//                                modifier = Modifier.padding(16.dp),
//                                color = Color.Black
//                            )
//                        }
//                        items(events.takeLast(3)) { event ->
//                            EventItem(event)
//                        }
//                        item {
//                            Divider(modifier = Modifier.padding(horizontal = 16.dp), color = Color.Gray)
//                        }
//                        item {
//                            Text(
//                                text = "All Events",
//                                style = MaterialTheme.typography.h6.copy(
//                                    fontWeight = FontWeight.Bold,
//                                    fontSize = 20.sp
//                                ),
//                                modifier = Modifier.padding(16.dp),
//                                color = Color.Black
//                            )
//                        }
//                        items(events) { event ->
//                            EventItem(event)
//                        }
//                    }
//                }
//            }
//        }
//    )
//}
//
//@Composable
//fun EventItem(event: EventData) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        elevation = 10.dp,
//        shape = MaterialTheme.shapes.medium,
//        backgroundColor = Color.LightGray
//    ) {
//        Column(
//            modifier = Modifier
//                .padding(16.dp)
//        ) {
//
//            Text(
//                text = event.eventHeadline,
//                style = MaterialTheme.typography.h6.copy(fontSize = 20.sp, fontWeight = FontWeight.Bold),
//                color = Color(0xFF6200EE)
//            )
//            Spacer(modifier = Modifier.height(6.dp))
//            Text(
//                text = event.eventDescription,
//                style = MaterialTheme.typography.body1.copy(fontSize = 16.sp),
//                color = Color(0xFF3700B3)
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
//                Icon(
//                    imageVector = Icons.Filled.DateRange,
//                    contentDescription = "Date",
//                    modifier = Modifier.size(24.dp),
//                    tint = Color(0xFF018786)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(
//                    text = event.eventDates,
//                    style = MaterialTheme.typography.body2.copy(fontSize = 14.sp),
//                    color = Color(0xFF018786)
//                )
//            }
//            Spacer(modifier = Modifier.height(8.dp))
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
//                Icon(
//                    imageVector = Icons.Filled.LocationOn,
//                    contentDescription = "Location",
//                    modifier = Modifier.size(24.dp),
//                    tint = Color(0xFF03DAC6)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(
//                    text = event.eventLocation,
//                    style = MaterialTheme.typography.body2.copy(fontSize = 14.sp),
//                    color = Color(0xFF03DAC6)
//                )
//            }
//            Spacer(modifier = Modifier.height(8.dp))
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
//                Icon(
//                    imageVector = Icons.Filled.Timeline,
//                    contentDescription = "Event Type",
//                    modifier = Modifier.size(24.dp),
//                    tint = Color(0xFF6200EE)
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                Text(
//                    text = event.eventType,
//                    style = MaterialTheme.typography.body2.copy(fontSize = 14.sp),
//                    color = Color(0xFF6200EE)
//                )
//            }
//        }
//    }
//}
//
