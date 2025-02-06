package com.example.readerapp.Screen.donationportal
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.readerapp.Donationdata3.StudentDonationViewModel
import com.example.readerapp.Navigation.ReaderScreens
import com.example.readerapp.R
import com.example.readerapp.Retrofit.ApiService
import com.example.readerapp.Retrofit.EventData
import com.example.readerapp.viewmodel.DonationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun BankDetailsScreen(
    navController: NavController,
    donationViewModel: DonationViewModel,
    apiService: ApiService

) {
    val donationState by donationViewModel.donation.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Donation Portal", style = MaterialTheme.typography.h6) },
                backgroundColor = Color(0xFF6200EE),
                contentColor = Color.White
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.bank),
                contentDescription = "Bank Details Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(8.dp)
            )
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Transfer your donation to the following account:",
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    elevation = 4.dp
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        if (donationState!=null){
                            val donation = donationState!!
                            Text("Account Holder: ${donation.accountHolderName}", fontSize = 16.sp)
                            Text("Bank Name: ${donation.bankName}", fontSize = 16.sp)
                            Text("Account Number: ${donation.accountNumber}", fontSize = 16.sp)
                            Text("IFSC Code: ${donation.ifscCode}", fontSize = 16.sp)

                        }
                        else {
                            Text("No donation details available.", fontSize = 16.sp, color = Color.Red)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick ={navController.navigate(ReaderScreens.DonationListforstudent.name) },
                modifier =Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6200EE))
            ) {
                Text("Donations", color = Color.White)
            }
            Button(
                onClick = {navController.navigate(ReaderScreens.DonationPortal2.name) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF03DAC5))
            ) {
                Text("Proceed to Submit Donation", color = Color.White)
            }
        }
    }
}

@Composable
fun DonationSubmissionScreen(
    navController: NavController,
    donationViewModel: StudentDonationViewModel,
    context: Context
) {
    var amount by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var batch by remember { mutableStateOf("") }
    var referenceId by remember { mutableStateOf("") }
    val timestamp = System.currentTimeMillis()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Donate", style = MaterialTheme.typography.h6) },
                backgroundColor = Color(0xFF6200EE),
                contentColor = Color.White
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Donation Amount") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Your Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = batch,
                onValueChange = { batch = it },
                label = { Text("Your Batch") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = referenceId,
                onValueChange = { referenceId = it },
                label = { Text("Reference ID") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (amount.isNotEmpty() && name.isNotEmpty() && batch.isNotEmpty() && referenceId.isNotEmpty()) {
                        donationViewModel.addStudentDonation(
                            amount = amount.toDouble(),
                            donorName = name,
                            batch = batch.toInt(),
                            referenceId = referenceId
                        )

                        Toast.makeText(context, "Donation Submitted Successfully!", Toast.LENGTH_SHORT).show()
                        navController.navigate(ReaderScreens.Thankyouscreen.name)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF03DAC5))
            ) {
                Text("Submit Donation", color = Color.White)
            }
        }
    }
}
@Composable
fun ThankYouScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Thank You!", style = MaterialTheme.typography.h6) },
                backgroundColor = Color(0xFF6200EE),
                contentColor = Color.White
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Thank you for your generous donation!",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Button(
                onClick = {
                    navController.navigate(ReaderScreens.DonationPortal.name)
                },
                modifier = Modifier.padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF03DAC5))
            ) {
                Text("Go Back", color = Color.White)
            }
        }
    }
}
