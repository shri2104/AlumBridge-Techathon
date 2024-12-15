package com.example.readerapp.Screen.jobs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.readerapp.jobData.JobPosting

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddJobScreen(navController: NavController, insertJob: (JobPosting) -> Unit) {
    var title by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
            TextField(value = company, onValueChange = { company = it }, label = { Text("Company") })
            TextField(value = role, onValueChange = { role = it }, label = { Text("Role") })
            TextField(value = description, onValueChange = { description = it }, label = { Text("Description") })

            Button(
                onClick = {
                    insertJob(JobPosting(title = title, company = company, role = role, description = description))
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Add Job")
            }
        }
    }
}
