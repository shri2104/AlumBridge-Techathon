package com.example.readerapp.Screen.jobs

import JobViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth()
        ) {
            Surface() {
                TopAppBar(
                    title = { Text(text = "Add Job Post") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }

                )
            }
            Column(
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth()
            ) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth())
                TextField(
                    value = company,
                    onValueChange = { company = it },
                    label = { Text("Company") },
                    )
                TextField(
                    value = role,
                    onValueChange = { role = it },
                    label = { Text("Role") },
                    )
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },

                )
                Button(
                    onClick = {
                        val newJob = JobPosting(
                            title = title,
                            company = company,
                            role = role,
                            description = description)
                        insertJob(newJob)
                        title = ""
                        company = ""
                        role = ""
                        description = ""
                        navController.popBackStack()
                    }
                ) {

                }
        }
    }
}
}



