package com.example.readerapp.Screen.jobs

import JobViewModel
import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.readerapp.jobData.JobPosting

@Composable
fun JobListScreen(
    navController: NavController,
    jobs : List<JobPosting>
){
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TopAppBar(
            title = { Text("Jobs")} ,
            navigationIcon ={
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }

        )

        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(jobs, key = { it.id }) { JobPosting ->
                Surface(
                    shadowElevation = 8.dp,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    JobRow(navController, modifier = Modifier.fillMaxWidth(), job = JobPosting)
                }
            }
        }

        Button(onClick = {navController.navigate("AddJobScreen")}, modifier = Modifier.fillMaxWidth()) {
            Text("Add Job Post")
        }
    }
}

@Composable
fun JobRow(navController: NavController,modifier: Modifier = Modifier, job: JobPosting) {
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(topEnd = 33.dp))
    ) {
        Column(modifier = modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = job.title
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = job.company,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = job.role,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = job.description,
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
