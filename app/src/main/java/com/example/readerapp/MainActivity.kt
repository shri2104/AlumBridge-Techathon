package com.example.readerapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.readerapp.Navigation.RNavigation
import com.example.readerapp.ui.theme.ReaderAppTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.readerapp.Retrofit.ApiService
import com.example.readerapp.Retrofit.createApiservice
import com.example.readerapp.Screen.Institute.AlumniHighlightsSection
import com.example.readerapp.Screen.Institute.RecentDonationsSection
import com.example.readerapp.Screen.Institute.Trendingevent
import com.example.readerapp.donationdata.TotalDonationViewModel
import com.example.readerapp.viewmodel.JobViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiService = createApiservice()
        enableEdgeToEdge()

        setContent {
            ReaderAppTheme {
                RNavigation(apiService)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RHomeScreen(
    navController: NavHostController,
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
                    Text("AlumBridge", style = MaterialTheme.typography.titleLarge)
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
                                navController.navigate("StudentProfile/$userId")
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
        bottomBar = { BottomNavigationBar(navController = navController,apiService,userId) }
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
                    text = "Welcome to Home Dashboard",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Your gateway to alumni features and updates",
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
                    StatisticsCard("Alumni", totalAlumni.toString(), Icons.Filled.Group)
                    StatisticsCard("Jobs", totalJobs.toString(), Icons.Filled.Work)
                    StatisticsCard("Donations", totalDonations.toString(), Icons.Filled.Favorite)
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
                RecentDonationsSection(apiService, userId = userId)

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
fun StatisticsCard(label: String, value: String, icon: ImageVector) {
    Card(
        shape = RoundedCornerShape(12.dp),
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
fun BottomNavigationBar(
    navController: NavHostController,
    apiService: ApiService,
    userId: String
) {
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
            onClick = {navController.navigate("postedEvent/$userId") },
            icon = { Icon(Icons.Filled.Event, contentDescription = "Events & Reunions") },
            label = { Text("Events") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("DonationList/$userId")  },
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
