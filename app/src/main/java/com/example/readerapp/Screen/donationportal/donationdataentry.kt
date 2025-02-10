package com.example.readerapp.Screen.donationportal
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bungalow
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.readerapp.Navigation.ReaderScreens
import com.example.readerapp.R
import com.example.readerapp.Retrofit.ApiService
import com.example.readerapp.Retrofit.DonationData
import com.example.readerapp.viewmodel.DonationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun DonationInputScreen(
    donationViewModel: DonationViewModel,
    navController: NavHostController,
    apiService: ApiService,
    userId: String
) {
    val coroutineScope = rememberCoroutineScope()
    var accountHolderName by remember { mutableStateOf(TextFieldValue()) }
    var bankName by remember { mutableStateOf(TextFieldValue()) }
    var accountNumber by remember { mutableStateOf(TextFieldValue()) }
    var ifscCode by remember { mutableStateOf(TextFieldValue()) }
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
                Image(
                    painter = painterResource(id = R.drawable.bank), // Replace with your image resource
                    contentDescription = "Image",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(bottom = 24.dp)
                )
                InputFieldWithIcon(
                    label = "Account Holder Name",
                    value = accountHolderName,
                    onValueChange = { accountHolderName = it },
                    icon = Icons.Filled.Person
                )
                Spacer(modifier = Modifier.height(16.dp))
                InputFieldWithIcon(
                    label = "Bank Name",
                    value = bankName,
                    onValueChange = { bankName = it },
                    icon = Icons.Filled.Bungalow
                )
                Spacer(modifier = Modifier.height(16.dp))
                InputFieldWithIcon(
                    label = "Account Number",
                    value = accountNumber,
                    onValueChange = { accountNumber = it },
                    icon = Icons.Filled.Numbers
                )
                Spacer(modifier = Modifier.height(16.dp))
                InputFieldWithIcon(
                    label = "IFSC Code",
                    value = ifscCode,
                    onValueChange = { ifscCode = it },
                    icon = Icons.Filled.Code
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
                        val donationdata = DonationData(
                            userId=userId,
                            AccountHolderName = accountHolderName.toString(),
                            BankName = bankName.toString(),
                            AccountNumber = accountNumber.toString(),
                            IFSCcode = ifscCode.toString(),
                        )
                        coroutineScope.launch(Dispatchers.IO) {
                            try {
                                apiService.StoreDonationdata(donationdata)
                            }
                            catch (e: Exception) {
                            }
                        }
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
fun InputFieldWithIcon(
    label: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    icon: ImageVector
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
}


@Composable
fun BankDetailsScreenForInstitute(
    navController: NavController,
    donationViewModel: DonationViewModel,
    apiservice: ApiService,
    userId: String
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp) // Space between buttons
            ) {
                Button(
                    onClick = { navController.navigate("DonationinfoEntry/$userId") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF03DAC5))
                ) {
                    Text("Update Bank Details", color = Color.White)
                }
                Button(
                    onClick = { navController.navigate("DonationList/$userId") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF03DAC5))
                ) {
                    Text("Donations", color = Color.White)
                }
                Button(
                    onClick = {navController.navigate("TotalDonation/$userId") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF03DAC5))
                ) {
                    Text("Enter Donation", color = Color.White)
                }
                Button(
                    onClick = { navController.navigate(ReaderScreens.RecievedDonations.name) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF03DAC5))
                ) {
                    Text("New Donations", color = Color.White)
                }
            }
        }
    }
}
