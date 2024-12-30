package com.example.readerapp.Screen.Login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.readerapp.Navigation.ReaderScreens
import com.example.readerapp.R
import com.example.readerapp.Screen.logo
import com.example.readerapp.component.EmailInput
import com.example.readerapp.component.InputField
import com.example.readerapp.component.PasswordInput
import com.example.readerapp.viewmodel.LoginScreenViewModel

@Composable
fun RLoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val showLoginForm = rememberSaveable { mutableStateOf(true) }
    val userType = rememberSaveable { mutableStateOf("student") }
    val instituteId = rememberSaveable { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            logo()
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Student",
                    fontWeight = if (userType.value == "student") FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.clickable { userType.value = "student" },
                    color = if (userType.value == "student") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Institute",
                    fontWeight = if (userType.value == "institute") FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.clickable { userType.value = "institute" },
                    color = if (userType.value == "institute") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                )
            }

            UserForm(
                loading = false,
                isCreateAccount = !showLoginForm.value,
                showInstituteIdField = userType.value == "student",
                instituteIdState = instituteId
            ) { email, password ->
                if (userType.value == "student") {
                    if (showLoginForm.value) {
                        // Sign-in or register as a student
                        viewModel.signInStudent(email, password, instituteId.value) {
                            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                        }
                    } else {
                        // Register student
                        viewModel.registerStudent(email, password, instituteId.value) {
                            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                        }
                    }
                } else {
                    if (showLoginForm.value) {

                        viewModel.signInWithEmailAndPassword(email, password, "institute") {
                            navController.navigate(ReaderScreens.InstituteHomeScreen.name)
                        }
                    } else {
                        // Register institute
                        viewModel.registerInstitute(email, password) {
                            navController.navigate(ReaderScreens.InstituteHomeScreen.name)
                        }
                    }
                }
            }

            // Toggle between login and register
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val actionText = if (showLoginForm.value) "Sign up" else "Log in"
                Text(
                    text = if (showLoginForm.value) "New User?" else "Already have an account?",
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = actionText,
                    modifier = Modifier
                        .clickable {
                            showLoginForm.value = !showLoginForm.value
                        }
                        .padding(start = 5.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}


@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    showInstituteIdField: Boolean = false,
    instituteIdState: MutableState<String> = rememberSaveable { mutableStateOf("") },
    onDone: (String, String) -> Unit = { email, pwd -> }
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current

    // States for error messages
    val emailError = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf("") }
    val instituteIdError = remember { mutableStateOf("") }

    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val instituteIdValid = remember { mutableStateOf(true) }
    val modifier = Modifier
        .height(250.dp)
        .background(MaterialTheme.colorScheme.background)
        .verticalScroll(rememberScrollState())

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        if (isCreateAccount) {
            Text(
                text = stringResource(id = R.string.create_acct),
                modifier = Modifier.padding(4.dp)
            )
        } else {
            Text("")
        }
        EmailInput(
            emailState = email, enabled = true,
            onAction = KeyboardActions {
                passwordFocusRequest.requestFocus()
            },
        )
        if (emailError.value.isNotEmpty()) {
            Text(
                text = emailError.value,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
        PasswordInput(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            passwordState = password,
            labelId = "Password",
            enabled = !loading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
            }
        )
        if (passwordError.value.isNotEmpty()) {
            Text(
                text = passwordError.value,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
        if (showInstituteIdField) {
            InputField(
                valueState = instituteIdState,
                labelId = "Institute ID",
                enabled = !loading,
                isSingleLine = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                onAction = KeyboardActions {
                    if (instituteIdState.value.trim().isNotEmpty()) {
                        onDone(email.value.trim(), password.value.trim())
                        keyboardController?.hide()
                    } else {
                        instituteIdError.value = "Please provide a valid Institute ID"
                    }
                }
            )
            if (instituteIdError.value.isNotEmpty()) {
                Text(
                    text = instituteIdError.value,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
    SubmitButton(
        textId = if (isCreateAccount) "Create Account" else "Login",
        loading = loading,
        validInputs = valid && (if (showInstituteIdField) instituteIdState.value.trim().isNotEmpty() else true),
        onClick = {
            // Check if fields are empty and set error messages
            if (email.value.trim().isEmpty()) {
                emailError.value = "Email cannot be empty"
            } else {
                emailError.value = ""
            }

            if (password.value.trim().isEmpty()) {
                passwordError.value = "Password cannot be empty"
            } else {
                passwordError.value = ""
            }
            if (showInstituteIdField && instituteIdState.value.trim().isEmpty()) {
                instituteIdError.value = "Institute ID cannot be empty"
            } else {
                instituteIdError.value = ""
            }
            if (emailError.value.isEmpty() && passwordError.value.isEmpty() && instituteIdError.value.isEmpty()) {
                onDone(email.value.trim(), password.value.trim())
                keyboardController?.hide()
            }
        }
    )
}

@Composable
fun SubmitButton(
    textId: String,
    loading: Boolean,
    validInputs: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = CircleShape
    ) {
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = textId, modifier = Modifier.padding(5.dp))
    }
}
