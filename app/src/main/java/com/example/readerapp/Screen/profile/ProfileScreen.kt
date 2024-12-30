package com.example.readerapp.Screen.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.readerapp.Navigation.ReaderScreens
import com.example.readerapp.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel
) {
    // Observe profile data from ViewModel
    val profile = viewModel.profile.collectAsStateWithLifecycle().value

    // Temporary state for editing
    var isEditing by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf(profile?.name ?: "") }
    var email by remember { mutableStateOf(profile?.email ?: "") }
    var year by remember { mutableStateOf(profile?.year ?: "") }
    var job by remember { mutableStateOf(profile?.job ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        ProfileField(
            label = "Name",
            value = name,
            isEditable = isEditing,
            onValueChange = { name = it }
        )
        ProfileField(
            label = "Email",
            value = email,
            isEditable = isEditing,
            onValueChange = { email = it }
        )
        ProfileField(
            label = "Passing Year",
            value = year,
            isEditable = isEditing,
            onValueChange = { year = it },
            keyboardType = KeyboardType.Number
        )
        ProfileField(
            label = "Job",
            value = job,
            isEditable = isEditing,
            onValueChange = { job = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Action Buttons
        if (isEditing) {
            Button(
                onClick = {
                    // Save changes to database via ViewModel
                    viewModel.saveProfile(name, email, year, job)
                    isEditing = false
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Changes")
            }
        } else {
            Button(
                onClick = { isEditing = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Filled.Edit, contentDescription = "Edit")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Edit Profile")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { FirebaseAuth.getInstance().signOut()
                navController.navigate(ReaderScreens.LoginScreen.name) },
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log Out")
        }
    }
}

@Composable
fun ProfileField(
    label: String,
    value: String,
    isEditable: Boolean,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = { if (isEditable) onValueChange(it) },
        label = { Text(label, color = Color.Black) },
        enabled = isEditable,
        readOnly = !isEditable,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        textStyle = TextStyle(color = Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}