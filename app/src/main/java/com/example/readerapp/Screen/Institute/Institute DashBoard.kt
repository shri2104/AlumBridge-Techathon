package com.example.readerapp.Screen.Institute


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.readerapp.Navigation.ReaderScreens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstituteDashBoard(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("AlumBridge", style = MaterialTheme.typography.titleLarge)
                },
                actions = {
                    IconButton(onClick = { navController.navigate(ReaderScreens.ProfileScreen.name) }) {
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
        bottomBar = { BottomInstituteNavigationBar(navController = navController) }
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
                    StatisticsCard("Alumni", "0", Icons.Filled.Group)
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
fun BottomInstituteNavigationBar(navController: NavController) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.primary,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(ReaderScreens.EventsPostings.name)},
            icon = { Icon(Icons.Filled.Event, contentDescription = "Events & Reunions") },
            label = { Text("Events") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Donation Portal") },
            label = { Text("Donations") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {navController.navigate(ReaderScreens.JobListScreen.name)  },
            icon = { Icon(Icons.Filled.Work, contentDescription = "Job Postings") },
            label = { Text("Jobs") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {navController.navigate(ReaderScreens.Directory.name)  },
            icon = { Icon(Icons.Filled.Group, contentDescription = "Alumni Directory") },
            label = { Text("Directory") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInstituteDashboard() {
    InstituteDashBoard(navController = NavController(LocalContext.current))
}
