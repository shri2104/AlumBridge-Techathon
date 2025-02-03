package com.example.readerapp.Screen.donationportal


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.readerapp.Donationdata3.StudentDonation
import com.example.readerapp.Donationdata3.StudentDonationViewModel
import java.text.SimpleDateFormat
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDonationListScreen(
    navController: NavController,
    donationViewModel: StudentDonationViewModel
) {
    val donations by donationViewModel.allStudentDonations.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Received Donations", style = MaterialTheme.typography.headlineMedium) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(Color.White)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (donations.isEmpty()) {
                Text(
                    text = "No donations yet.",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(donations) { donation ->
                        DonationItem(donation)
                    }
                }
            }
        }
    }
}

@Composable
fun DonationItem(donation: StudentDonation) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Amount: ₹${donation.amount}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Donor Name: ${donation.donorName}",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Batch: ${donation.batch}",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Reference ID: ${donation.id}",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Donation Timestamp: ${SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Date(donation.timestamp))}",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
