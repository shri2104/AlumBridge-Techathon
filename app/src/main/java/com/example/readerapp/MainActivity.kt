package com.example.readerapp

import android.os.Bundle
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
import androidx.compose.ui.draw.shadow

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import com.example.readerapp.Navigation.ReaderScreens



@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReaderAppTheme {
                RNavigation()
            }
        }
    }
}


@Composable
fun ReaderApp() {
    Surface(color=MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 46.dp)) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            RNavigation()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RHomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("AlumBridge", style = MaterialTheme.typography.titleLarge)
                },
                actions = {
                    IconButton(onClick = {navController.navigate(ReaderScreens.ProfileScreen.name) }) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Profile",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .shadow(4.dp, RoundedCornerShape(0.dp))
            )
        },
        bottomBar = { BottomNavigationBar(navController=navController) }
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
                    StatisticsCard("Students", "0", Icons.Filled.Group)
                    StatisticsCard("Jobs", "0", Icons.Filled.Work)
                    StatisticsCard("Donations", "$0", Icons.Filled.Favorite)
                }
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
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        tonalElevation = 8.dp // Apply tonal elevation for shadow
    ) {
        NavigationBarItem(
            selected = false,
            onClick = {navController.navigate(ReaderScreens.postedEvents.name)   },
            icon = { Icon(Icons.Filled.Event, contentDescription = "Events & Reunions") },
            label = { Text("Events") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {navController.navigate(ReaderScreens.DonationPortal.name)  },
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Donation Portal") },
            label = { Text("Donations") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(ReaderScreens.JobListScreen.name) },
            icon = { Icon(Icons.Filled.Work, contentDescription = "Job Postings") },
            label = { Text("Jobs") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {navController.navigate(ReaderScreens.Directory.name)   },
            icon = { Icon(Icons.Filled.Group, contentDescription = "Alumni Directory") },
            label = { Text("Directory") }
        )
    }
}
