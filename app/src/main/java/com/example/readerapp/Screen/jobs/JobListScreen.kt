import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.readerapp.Retrofit.ApiService
import com.example.readerapp.Retrofit.JobPosting

import com.example.readerapp.viewmodel.JobViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobListScreen(
    navController: NavController,
    apiService: ApiService,
    JobViewModel: JobViewModel,
    userId: String
) {
    val jobList = remember { mutableStateListOf<JobPosting>() }
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    // Fetch jobs specific to userId
    LaunchedEffect(userId) {
        try {
            val response = apiService.getJobsByUser(userId) // Call the API with userId
            if (response.isNotEmpty()) {
                jobList.clear()
                jobList.addAll(response)
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Available Jobs", style = MaterialTheme.typography.titleLarge) },
                actions = {
                    IconButton(onClick = { navController.navigate("AddJobScreen/$userId") }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Job",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        content = { paddingValues ->
            JobListContent(
                jobs = jobList,
                expanded = expanded,
                onExpandChanged = { expanded = it },
                navController = navController,
                apiService = apiService,
                modifier = Modifier.padding(paddingValues)
            )
        }
    )
}


@Composable
fun JobListContent(
    jobs: List<JobPosting>,
    expanded: Boolean,
    onExpandChanged: (Boolean) -> Unit,
    navController: NavController,
    apiService: ApiService,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        if (jobs.isEmpty()) {
            Text(
                text = "No jobs available at the moment",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 16.dp)
            )
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(jobs) { job ->
                    JobCard(job)
                }
            }
        }
    }
}

@Composable
fun JobCard(job: JobPosting) {
    val context = LocalContext.current
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp,
        color = Color.LightGray
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "Job Title: ${job.title}",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Company: ${job.company}",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Role: ${job.role}",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Job Description: ${job.description}",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            // Apply Button
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { openApplyLink(job.applyLink,context) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Apply Now", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}

fun openApplyLink(url: String, context: Context) {
    val context = context
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}
