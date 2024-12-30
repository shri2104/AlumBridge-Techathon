package com.example.readerapp.Screen.donationportal
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.readerapp.Navigation.ReaderScreens
import com.example.readerapp.R
import com.example.readerapp.viewmodel.DonationViewModel
import com.example.readerapp.viewmodel.ProfileViewModel


@Composable
fun DonationInputScreen(
    donationViewModel: DonationViewModel,
    navController: NavHostController
) {
    var accountHolderName by remember { mutableStateOf(TextFieldValue()) }
    var bankName by remember { mutableStateOf(TextFieldValue()) }
    var accountNumber by remember { mutableStateOf(TextFieldValue()) }
    var ifscCode by remember { mutableStateOf(TextFieldValue()) }

    // Derived state to check if all fields are filled
    val isButtonEnabled = accountHolderName.text.isNotBlank() &&
            bankName.text.isNotBlank() &&
            accountNumber.text.isNotBlank() &&
            ifscCode.text.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Donation Information") },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                InputField(
                    label = "Account Holder Name",
                    value = accountHolderName,
                    onValueChange = { accountHolderName = it }
                )
                Spacer(modifier = Modifier.height(16.dp))
                InputField(
                    label = "Bank Name",
                    value = bankName,
                    onValueChange = { bankName = it }
                )
                Spacer(modifier = Modifier.height(16.dp))
                InputField(
                    label = "Account Number",
                    value = accountNumber,
                    onValueChange = { accountNumber = it }
                )
                Spacer(modifier = Modifier.height(16.dp))
                InputField(
                    label = "IFSC Code",
                    value = ifscCode,
                    onValueChange = { ifscCode = it }
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = {
                        donationViewModel.saveDonation(
                            accountHolderName.text,
                            bankName.text,
                            accountNumber.text,
                            ifscCode.text
                        )
                        navController.popBackStack()
                    },
                    enabled = isButtonEnabled,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Donation", fontSize = 18.sp)
                }
            }
        }
    )
}

@Composable
fun InputField(
    label: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontSize = 14.sp, color = MaterialTheme.colors.onBackground)
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, MaterialTheme.colors.primary)
                .padding(8.dp),
            singleLine = true
        )
    }
}

@Composable
fun BankDetailsScreenForInstitute(
    navController: NavController,
    donationViewModel: DonationViewModel
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
                    "Your Bank Details: ",
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
                        if (donationState != null) {
                            val donation = donationState!!
                            Text("Account Holder: ${donation.accountHolderName}", fontSize = 16.sp)
                            Text("Bank Name: ${donation.bankName}", fontSize = 16.sp)
                            Text("Account Number: ${donation.accountNumber}", fontSize = 16.sp)
                            Text("IFSC Code: ${donation.ifscCode}", fontSize = 16.sp)
                        } else {
                            Text("No donation details available.", fontSize = 16.sp, color = Color.Red)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate(ReaderScreens.DonationInfoEntry.name) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF03DAC5))
            ) {
                Text("Update Bank Details", color = Color.White)
            }
        }
    }
}