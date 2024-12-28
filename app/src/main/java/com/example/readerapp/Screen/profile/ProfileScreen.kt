package com.example.readerapp.Screen.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    initialName: String = "",
    initialEmail: String = "",
    initialYear: String = "",
    initialJob: String = ""
) {
    // Saved data for each field
    var savedName by remember { mutableStateOf(initialName) }
    var savedEmail by remember { mutableStateOf(initialEmail) }
    var savedYear by remember { mutableStateOf(initialYear) }
    var savedJob by remember { mutableStateOf(initialJob) }

    // Temporary editable state for each field
    var name by remember { mutableStateOf(TextFieldValue(savedName)) }
    var email by remember { mutableStateOf(TextFieldValue(savedEmail)) }
    var year by remember { mutableStateOf(TextFieldValue(savedYear)) }
    var job by remember { mutableStateOf(TextFieldValue(savedJob)) }

    // Editing mode
    var isEditing by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Title
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Profile Fields
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
                    // Save changes to actual data
                    savedName = name.text
                    savedEmail = email.text
                    savedYear = year.text
                    savedJob = job.text
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

        // Log Out Button
        Button(
            onClick = { navController.popBackStack() },
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
    value: TextFieldValue,
    isEditable: Boolean,
    onValueChange: (TextFieldValue) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = { if (isEditable) onValueChange(it) },
        label = { Text(label,color = Color.Black) },
        enabled = isEditable,
        readOnly = !isEditable,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        textStyle = TextStyle(
            color = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

