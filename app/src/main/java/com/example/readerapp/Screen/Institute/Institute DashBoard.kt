package com.example.readerapp.Screen.Institute

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.readerapp.Donationdata2.TotalDonation
import com.example.readerapp.Navigation.ReaderScreens
import com.example.readerapp.R
import com.example.readerapp.donationdata.TotalDonationViewModel
import com.example.readerapp.viewmodel.JobViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstituteDashBoard(
    navController: NavController,
    jobViewModel: JobViewModel,
    totalDonationViewModel: TotalDonationViewModel,
    userId: String
) {
    val totalJobs by jobViewModel.totalJobCount.collectAsState()
    val donations = totalDonationViewModel.allTotalDonations.collectAsState(initial = emptyList())
    val totalDonationAmount = donations.value.sumOf { it.amount }.toString()

    var expanded by remember { mutableStateOf(false) }

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
        bottomBar = { BottomInstituteNavigationBar(navController = navController,userId) }
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

                // Statistics Section
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
                    StatisticsCard("Alumni", "0", Icons.Filled.Group)
                    StatisticsCard("Jobs", totalJobs.toString(), Icons.Filled.Work) // Display total jobs
                    StatisticsCard("Donations", "₹$totalDonationAmount", Icons.Filled.Favorite) // Display total donation amount
                }

                // Trending Events Section
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Trending Events",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                TrendingEventsSection(navController)

                // Recent Donations Section
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Recent Donations",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                RecentDonationsSection(donations.value)

                // Alumni Highlights Section
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
fun TrendingEventsSection(navController: NavController) {
    // Display a list of upcoming or trending events
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation =  CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Event 1: Alumni Meetup", fontWeight = FontWeight.Bold)
            Text(text = "Join us for an exciting alumni meetup.", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { navController.navigate("EventDetailsScreen") }) {
                Text("View Details")
            }
        }
    }
}

@Composable
fun RecentDonationsSection(donations: List<TotalDonation>) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation =  CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            donations.take(3).forEach {
                Text(text = "₹${"it.amount"} donated by ${"it.donorName"}")
            }
        }
    }
}

@Composable
fun AlumniHighlightsSection() {
    // Display a highlight for an alumnus
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
            Button(onClick = { /* Navigate to alumni details */ }) {
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
            onClick = { navController.navigate(ReaderScreens.InstituteHomeScreen.name) },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("Eventpost/$userId") },
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
            onClick = {navController.navigate("AddJobScreen/$userId") },
            icon = { Icon(Icons.Filled.Work, contentDescription = "Job Postings") },
            label = { Text("Jobs") }
        )
    }
}