package com.example.readerapp.Screen.profile

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.readerapp.R
import com.example.readerapp.Retrofit.ApiService
import com.example.readerapp.Retrofit.InstituteData


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Instituteprofilepage(
    navController: NavController,
    userId: String,
    apiService: ApiService
) {
    var instituteData by remember { mutableStateOf<InstituteData?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val screenBackgroundColor = MaterialTheme.colorScheme.background

    LaunchedEffect(userId) {
        if (!userId.isNullOrEmpty()) {
            isLoading = true
            try {
                instituteData = apiService.Getinstitutedata(userId)
            } catch (e: Exception) {
                Log.e("API Error", "Error fetching institute data: ${e.message}", e)
                Toast.makeText(context, "Error fetching institute data: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.logo_no_bg),
                            contentDescription = "Institute Logo",
                            modifier = Modifier
                                .size(80.dp)
                                .padding(end = 8.dp)
                        )
                    }
                },
                actions = {  }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(screenBackgroundColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                instituteData?.let { institute ->
                    Column(
                        modifier = Modifier.fillMaxSize().padding(top = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Institute Profile",
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = institute.name,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            item {
                                InstituteProfileCard(institute)
                            }
                        }
                    }
                } ?: run {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "No institute data available.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InstituteProfileCard(institute: InstituteData) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileDetailWithElevation(value = institute.name, icon = Icons.Default.School, onEditClick = { })
            ProfileDetailWithElevation(value = institute.type, icon = Icons.Default.Business, onEditClick = { })
            ProfileDetailWithElevation(value = institute.location, icon = Icons.Default.Place, onEditClick = { })
            ProfileDetailWithElevation(value = institute.establishmentYear, icon = Icons.Default.CalendarToday, onEditClick = { })
            ProfileDetailWithElevation(value = institute.address, icon = Icons.Default.Home, onEditClick = { })
            ProfileDetailWithElevation(value = institute.affiliation, icon = Icons.Default.Star, onEditClick = { })
            ProfileDetailWithElevation(value = institute.contactEmail, icon = Icons.Default.Email, onEditClick = { })
            ProfileDetailWithElevation(value = institute.contactNumber, icon = Icons.Default.Phone, onEditClick = { })
            ProfileDetailWithElevation(value = institute.websiteURL, icon = Icons.Default.Public, onEditClick = { })
        }
    }
}
@Composable
fun ProfileDetailWithElevation(value: String, icon: ImageVector, onEditClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(elevation = 4.dp, shape = MaterialTheme.shapes.medium),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Edit",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
