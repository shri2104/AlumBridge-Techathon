//package com.example.readerapp.Screen.profile
//
//import android.util.EventLogTags
//import android.widget.Toast
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material.icons.filled.ArrowDropDown
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.navigation.NavController
//import com.example.readerapp.Retrofit.ApiService
//import com.example.readerapp.Retrofit.EventData
//import com.example.readerapp.Retrofit.ProfileData
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ProfileData(navController: NavController, apiService: ApiService) {
//    val coroutineScope = rememberCoroutineScope()
//    val context = LocalContext.current
//
//    var name by remember { mutableStateOf("") }
//    var email by remember { mutableStateOf("") }
//    var phonenumber by remember { mutableStateOf("") }
//    var location by remember { mutableStateOf("") }
//    var batch by remember { mutableStateOf("") }
//    var currentworkstatus by remember { mutableStateOf("") }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Profile", fontWeight = FontWeight.Bold) },
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            )
//        },
//        bottomBar = {
//            BottomAppBar {
//                Text(
//                    text = "Powered by Alumni Network",
//                    modifier = Modifier.fillMaxWidth(),
//                    textAlign = TextAlign.Center,
//                    fontWeight = FontWeight.SemiBold
//                )
//            }
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(10.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//
//            InputFields("Enter Name") { name = it }
//            InputFields("Enter Email") { email = it }
//            InputFields("Enter PhoneNumber") { phonenumber = it }
//            InputFields("Enter Location") { location = it }
//            InputFields("Enter Batch") { batch = it } // Fixed
//            InputFields("Your Current Work Status"){ currentworkstatus = it }
//            Button(
//                onClick = {
//                    val profileData = ProfileData(
//
//                        Name = name,
//                        Email = email,
//                        Phonenumber = phonenumber,
//                        Location = location,
//                        Batch = batch,
//                        CurrentworkStatus = currentworkstatus
//                    )
////                    coroutineScope.launch(Dispatchers.IO) {
////                        try {
////                            val response = apiService.Storeprofiledata(profileData)
////
////                        } catch (e: Exception) {
////                        }
////                    }
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Submit")
//            }
//        }
//    }
//}
//
//@Composable
//fun InputFields(placeholder: String, onValueChange: (String) -> Unit) {
//    var inputData by remember { mutableStateOf("") }
//    Card(
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
//        shape = RoundedCornerShape(8.dp),
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(bottom = 16.dp)
//    ) {
//        TextField(
//            value = inputData,
//            onValueChange = {
//                inputData = it
//                onValueChange(it)
//            },
//            placeholder = { Text(text = placeholder) },
//            modifier = Modifier.fillMaxWidth(),
//            colors = TextFieldDefaults.colors(
//                unfocusedIndicatorColor = Color.Transparent,
//                focusedIndicatorColor = Color.Transparent,
//                cursorColor = Color.Blue,
//                focusedContainerColor = Color.White,
//                unfocusedContainerColor = Color.White
//            )
//        )
//    }
//}
//
//
//
