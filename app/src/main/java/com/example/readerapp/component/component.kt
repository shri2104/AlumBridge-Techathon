package com.example.readerapp.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    labelId: String = "Email",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    val emailValid = remember(emailState.value) {
        emailState.value.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(emailState.value).matches()
    }
    InputField(
        modifier = modifier,
        valueState = emailState,
        labelId = labelId,
        enabled = enabled,
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        onAction = onAction,
        isError = !emailValid,
        errorMessage = if (!emailValid) "Please provide a valid email address" else ""
    )
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    isError: Boolean = false,
    errorMessage: String = ""
) {
    Column {
        OutlinedTextField(
            value = valueState.value,
            onValueChange = { valueState.value = it },
            label = { Text(text = labelId) },
            singleLine = isSingleLine,
            textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground),
            modifier = modifier
                .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
                .fillMaxWidth(),
            enabled = enabled,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            keyboardActions = onAction,
            isError = isError
        )

        // Display error message if the email is invalid
        if (isError) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
    }
}

@Composable
fun PasswordInput(
    modifier: Modifier,
    passwordState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    passwordVisibility: MutableState<Boolean>,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default,
) {

    // Toggle between plain text and hidden password
    val visualTransformation = if (passwordVisibility.value) VisualTransformation.None else
        PasswordVisualTransformation()  // Hides the password

    OutlinedTextField(
        value = passwordState.value,
        onValueChange = {
            passwordState.value = it
        },
        label = { Text(text = labelId) },
        singleLine = true,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        visualTransformation = visualTransformation,  // Apply transformation based on visibility
        trailingIcon = {
            PasswordVisibility(passwordVisibility = passwordVisibility) // Toggle password visibility
        },
        keyboardActions = onAction
    )
}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    // Toggle visibility icon (eye icon)
    val icon = if (passwordVisibility.value) {
        Icons.Default.Visibility  // Show password icon
    } else {
        Icons.Default.VisibilityOff  // Hide password icon
    }

    IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
        Icon(imageVector = icon, contentDescription = "Toggle Password Visibility")
    }
}
