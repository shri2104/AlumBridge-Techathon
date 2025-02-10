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
import com.example.readerapp.Retrofit.ProfileData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentRegistrationForm(
    userId: String,
    navController: NavController,
    apiService: ApiService
) {
    val coroutineScope = rememberCoroutineScope()

    // State variables for student profile details
    var studentName by remember { mutableStateOf(TextFieldValue("")) }
    var studentEmail by remember { mutableStateOf(TextFieldValue("")) }
    var studentPhoneNumber by remember { mutableStateOf(TextFieldValue("")) }
    var studentLocation by remember { mutableStateOf(TextFieldValue("")) }
    var studentBatch by remember { mutableStateOf(TextFieldValue("")) }
    var studentWorkStatus by remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Student Registration", style = MaterialTheme.typography.headlineMedium) }
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
            item {
                CustomTextField(value = studentName, onValueChange = { studentName = it }, label = "Student Name")
            }
            item {
                CustomTextField(value = studentEmail, onValueChange = { studentEmail = it }, label = "Email")
            }
            item {
                CustomTextField(value = studentPhoneNumber, onValueChange = { studentPhoneNumber = it }, label = "Phone Number")
            }
            item {
                CustomTextField(value = studentLocation, onValueChange = { studentLocation = it }, label = "Location")
            }
            item {
                CustomTextField(value = studentBatch, onValueChange = { studentBatch = it }, label = "Batch")
            }
            item {
                CustomTextField(value = studentWorkStatus, onValueChange = { studentWorkStatus = it }, label = "Current Work Status")
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        // Creating the ProfileData object for student
                        val studentProfile = ProfileData(
                            userId = userId,
                            Name = studentName.text,
                            Email = studentEmail.text,
                            Phonenumber = studentPhoneNumber.text,
                            Location = studentLocation.text,
                            Batch = studentBatch.text,
                            CurrentworkStatus = studentWorkStatus.text
                        )
                        // Making the API call to register the student profile
                        coroutineScope.launch(Dispatchers.IO) {
                            try {
                                val response = apiService.StoreProfiledata(studentProfile)
                                if (response.isSuccessful) {


                                } else {

                                }
                            } catch (e: Exception) {

                            }
                        }
                        navController.navigate("ReaderHomeScreen/$userId")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Submit")
                }
            }
        }
    }
}

