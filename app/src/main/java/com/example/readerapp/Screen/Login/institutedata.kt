package com.example.readerapp.Screen.Login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.readerapp.Retrofit.ApiService
import com.example.readerapp.Retrofit.InstituteData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstituteRegistrationForm(
    userId: String,
    navController: NavController,
    apiService: ApiService
) {
    val coroutineScope = rememberCoroutineScope()
    // State variables to hold input values
    var instituteName by remember { mutableStateOf(TextFieldValue("")) }
    var instituteType by remember { mutableStateOf(TextFieldValue("")) }
    var location by remember { mutableStateOf(TextFieldValue("")) }
    var establishmentYear by remember { mutableStateOf(TextFieldValue("")) }
    var address by remember { mutableStateOf(TextFieldValue("")) }
    var affiliation by remember { mutableStateOf(TextFieldValue("")) }
    var contactEmail by remember { mutableStateOf(TextFieldValue("")) }
    var contactNumber by remember { mutableStateOf(TextFieldValue("")) }
    var websiteURL by remember { mutableStateOf(TextFieldValue("")) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Institute Registration", style = MaterialTheme.typography.headlineMedium) }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Input fields for institute details
            item {
                CustomTextField(value = instituteName, onValueChange = { instituteName = it }, label = "Institute Name")
            }
            item {
                CustomTextField(value = instituteType, onValueChange = { instituteType = it }, label = "Institute Type")
            }
            item {
                CustomTextField(value = location, onValueChange = { location = it }, label = "Location")
            }
            item {
                CustomTextField(value = establishmentYear, onValueChange = { establishmentYear = it }, label = "Establishment Year")
            }
            item {
                CustomTextField(value = address, onValueChange = { address = it }, label = "Address")
            }
            item {
                CustomTextField(value = affiliation, onValueChange = { affiliation = it }, label = "Affiliation")
            }
            item {
                CustomTextField(value = contactEmail, onValueChange = { contactEmail = it }, label = "Contact Email")
            }
            item {
                CustomTextField(value = contactNumber, onValueChange = { contactNumber = it }, label = "Contact Number")
            }
            item {
                CustomTextField(value = websiteURL, onValueChange = { websiteURL = it }, label = "Website URL")
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        val instituteData = InstituteData(
                            userId = userId,
                            name = instituteName.text,
                            type = instituteType.text,
                            location = location.text,
                            establishmentYear = establishmentYear.text,
                            address = address.text,
                            affiliation = affiliation.text,
                            contactEmail = contactEmail.text,
                            contactNumber = contactNumber.text,
                            websiteURL = websiteURL.text,
                        )
                        coroutineScope.launch(Dispatchers.IO) {
                            try {
                                val response = apiService.Institutedata(instituteData)
                            } catch (e: Exception) {
                            }
                        }
                        navController.navigate("InstituteHomeScreen/$userId")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Submit")
                }
            }
        }
    }
}

@Composable
fun CustomTextField(value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit, label: String) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = "Enter $label") },
            label = { Text(text = label) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Blue,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewInstituteRegistrationForm() {
//    InstituteRegistrationForm(navController, apiService)
//}
