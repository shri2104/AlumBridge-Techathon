package com.example.readerapp.Screen.donationportal

import androidx.compose.material.icons.Icons
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.readerapp.donationdata.TotalDonationViewModel
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Favorite
import com.example.readerapp.Navigation.ReaderScreens
import com.example.readerapp.Retrofit.ApiService
import com.example.readerapp.Retrofit.Donatedinfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TDonationInputScreen(
    navController: NavController,
    totalDonationViewModel: TotalDonationViewModel,
    apiservice: ApiService,
    userId: String
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var amount by remember { mutableStateOf("") }
    var donorName by remember { mutableStateOf("") }
    var batchof by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Donation Input", fontSize = 20.sp) },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Donations")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Enter Donation Information", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Amount", style = MaterialTheme.typography.bodyLarge)
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Donor Name", style = MaterialTheme.typography.bodyLarge)
            OutlinedTextField(
                value = donorName,
                onValueChange = { donorName = it },
                label = { Text("Donor Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Batch", style = MaterialTheme.typography.bodyLarge)
            OutlinedTextField(
                value = batchof,
                onValueChange = { batchof = it },
                label = { Text("Batch") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                if (amount.isNotBlank() && donorName.isNotBlank() && batchof.isNotBlank()) {
                    val donateddata = Donatedinfo(
                        userId=userId,
                        Amount = amount,
                        Donarname = donorName,
                        Batch = batchof,
                        timestamp = System.currentTimeMillis(),
                    )
                    coroutineScope.launch(Dispatchers.IO) {
                        try {
                            val response = apiservice.StoreDonationInfo(donateddata)
                        } catch (e: Exception) {
                        }
                    }
                    val batchofInt = batchof.toIntOrNull() ?: 0
                    totalDonationViewModel.addTotalDonation(amount.toDouble(), donorName, batchofInt)
                    Toast.makeText(context, "Donation Added!", Toast.LENGTH_SHORT).show()
                    navController.navigate(ReaderScreens.DonationList.name)

                } else {
                    Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Submit Donation")
            }
        }
    }
}

