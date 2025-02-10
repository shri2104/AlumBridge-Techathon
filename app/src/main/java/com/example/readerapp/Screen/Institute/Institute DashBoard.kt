package com.example.readerapp.Screen.Institute

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.readerapp.Navigation.ReaderScreens
import com.example.readerapp.R
import com.example.readerapp.Retrofit.ApiService
import com.example.readerapp.Retrofit.Donatedinfo
import com.example.readerapp.Retrofit.EventData
import com.example.readerapp.donationdata.TotalDonationViewModel
import com.example.readerapp.viewmodel.JobViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstituteDashBoard(
    navController: NavController,
    jobViewModel: JobViewModel,
    totalDonationViewModel: TotalDonationViewModel,
    userId: String,
    apiService: ApiService
) {
    val context = LocalContext.current
    var totalJobs by remember { mutableStateOf(0) }
    var totalAlumni by remember { mutableStateOf(0) }
    var totalDonations by remember { mutableStateOf(0) }  // Store total donation count

    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(userId) {
        try {
            // Fetch the total number of jobs
            val jobsResponse = apiService.getJobsByUser(userId)
            totalJobs = jobsResponse.size

            // Fetch the alumni profile data (if needed)
            val profilesResponse = apiService.getprofile(userId)
            totalAlumni = profilesResponse.size

            // Fetch total donations for the user
            val donationResponse = apiService.Getalldonation(userId)
            totalDonations = donationResponse.size  // Count total donations

        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.logo_no_bg), // Replace with your logo resource
                        contentDescription = "App Logo",
                        modifier = Modifier.size(120.dp)
                    )
                },
                actions = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Profile Icon",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                navController.navigate("Instituteprfilepage/$userId")
                                expanded = false
                            },
                            text = { Text("Profile") }
                        )
                        DropdownMenuItem(
                            onClick = {
                                navController.navigate("Directory/$userId")
                                expanded = false
                            },
                            text = { Text("Directory") }
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f),
                    titleContentColor = MaterialTheme.colorScheme.onSecondary
                ),
                modifier = Modifier
                    .shadow(4.dp, RoundedCornerShape(0.dp))
            )
        },
        bottomBar = { BottomInstituteNavigationBar(navController = navController, userId) }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = "Welcome to Institute Dashboard",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Manage your alumni network and institute features",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = "Statistics",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    StatisticsCard("Alumni", totalAlumni.toString(), Icons.Filled.Group) // Display total alumni count
                    StatisticsCard("Jobs", totalJobs.toString(), Icons.Filled.Work) // Display total jobs
                    StatisticsCard("Donations", totalDonations.toString(), Icons.Filled.Favorite) // Display total donations count
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Trending Events",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Trendingevent(navController, apiService, userId)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Recent Donations",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                RecentDonationsSection(
                    apiService,
                    userId = userId
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Alumni Highlights",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                AlumniHighlightsSection()
            }
        }
    }
}



@Composable
fun Trendingevent(navController: NavController, apiService: ApiService, userId: String) {
    val context = LocalContext.current
    val latestEvents = remember { mutableStateOf<List<EventData>>(emptyList()) } // Store the latest events

    LaunchedEffect(Unit) {
        try {
            val response = apiService.getAllEvents(userId)

            Log.d("TrendingEvent", "Response: $response")
            val sortedEvents = response
                .filter { it.createdAt != null }
                .sortedByDescending { parseDateToTimestamp((it.createdAt ?: "").toString()) }
            latestEvents.value = sortedEvents.take(2)
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    // Display the latest two events
    if (latestEvents.value.isNotEmpty()) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            latestEvents.value.forEach { event ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // Handle navigation when the card is clicked
                            navController.navigate(
                                "eventDetailScreen/${event.userId}/${event.Headline}/${event.Description}/${event.Dates}/${event.Location}/${event.Forms}/${event.EventType}"
                            )
                        }
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // Event headline
                        Text(text = event.Headline ?: "No headline", fontWeight = FontWeight.Bold)

                        // Event date
                        Text(text = event.Dates ?: "No date available", style = MaterialTheme.typography.bodyMedium)

                        Spacer(modifier = Modifier.height(8.dp))

                        // View Details Button
                        Button(onClick = {
                            navController.navigate(
                                "eventDetailScreen/${event.userId}/${event.Headline}/${event.Description}/${event.Dates}/${event.Location}/${event.Forms}/${event.EventType}"
                            )
                        }) {
                            Text("View Details")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp)) // Add some space between the events
            }
        }
    } else {
        // Display a message if no events are available
        Text(text = "No events available.", style = MaterialTheme.typography.bodyMedium)
    }
}


// You need to define your own `parseDateToTimestamp` function
fun parseDateToTimestamp(dateString: String): Long {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()) // Adjust format as needed
        val date = sdf.parse(dateString)
        date?.time ?: 0L
    } catch (e: ParseException) {
        Log.e("TrendingEvent", "Error parsing date: $e")
        0L
    }
}




@Composable
fun RecentDonationsSection(apiService: ApiService, userId: String) {
    val context = LocalContext.current
    val donations = remember { mutableStateOf<List<Donatedinfo>>(emptyList()) } // Store the donations

    // Fetch the latest donations when the Composable is launched
    LaunchedEffect(userId) {
        try {
            val response = apiService.Getalldonation(userId) // Fetch donations from API

            // Log the response for debugging
            Log.d("RecentDonations", "Response: $response")

            // Sort the donations by amount or timestamp (you can use timestamp if available)
            val sortedDonations = response
                .sortedByDescending { it.Amount?.toIntOrNull() ?: 0 } // Sort by amount (descending order)
                .take(2) // Take the latest two donations

            donations.value = sortedDonations
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    if (donations.value.isNotEmpty()) {
        donations.value.forEach { donation ->
            Card(
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "₹${donation.Amount} donated by ${donation.Donarname}")
                }
            }
        }
    } else {
        // Display a message if no donations are available
        Text(text = "No donations available.", style = MaterialTheme.typography.bodyMedium)
    }
}


@Composable
fun AlumniHighlightsSection() {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Alumni of the Month: John Doe", fontWeight = FontWeight.Bold)
            Text(text = "John is a successful entrepreneur, creating impactful solutions in the tech industry.", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { }) {
                Text("Read More")
            }
        }
    }
}

@Composable
fun QuickLinksSection(navController: NavController) {
    // Display cards or buttons for quick navigation to frequently used sections
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        QuickLinkCard("Jobs", Icons.Filled.Work, { navController.navigate(ReaderScreens.JobListScreen.name) })
        QuickLinkCard("Events", Icons.Filled.Event, { navController.navigate("Eventpost") })
        QuickLinkCard("Donations", Icons.Filled.Favorite, { navController.navigate(ReaderScreens.DonationInfo.name) })
    }
}

@Composable
fun QuickLinkCard(label: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation =  CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .size(100.dp)
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(icon, contentDescription = label, modifier = Modifier.size(32.dp))
            Text(text = label, fontSize = 12.sp)
        }
    }
}


@Composable
fun StatisticsCard(label: String, value: String, icon: ImageVector) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation( 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier
            .size(120.dp)
            .padding(4.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(icon, contentDescription = label, modifier = Modifier.size(32.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = label,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun BottomInstituteNavigationBar(navController: NavController, userId: String) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.9f),
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = false,
            onClick = {  },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("postedEvent/$userId") },
            icon = { Icon(Icons.Filled.Event, contentDescription = "Events & Reunions") },
            label = { Text("Events") }
        )

        NavigationBarItem(
            selected = false,
            onClick = {  navController.navigate("Donationdashboard/$userId") },
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Donation Portal") },
            label = { Text("Donations") }
        )

        NavigationBarItem(
            selected = false,
            onClick = {navController.navigate("JobListScreen/$userId") },
            icon = { Icon(Icons.Filled.Work, contentDescription = "Job Postings") },
            label = { Text("Jobs") }
        )
    }
}