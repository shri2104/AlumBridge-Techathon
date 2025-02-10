package com.examplepackage

import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import com.example.readerapp.Retrofit.ApiService
import com.example.readerapp.Retrofit.emailuserid
import com.example.readerapp.Screen.Login.LoginScreenViewModel
import com.example.readerapp.Screen.logo
import com.example.readerapp.component.EmailInput
import com.example.readerapp.component.InputField
import com.example.readerapp.component.PasswordInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

@Composable
fun RLoginScreen(
    navController: NavController,
    apiService: ApiService,
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val showLoginForm = rememberSaveable { mutableStateOf(true) }
    val userType = remember { mutableStateOf("student") }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            logo()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Student",
                    fontWeight = if (userType.value == "student") FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.clickable { userType.value = "student" },
                    color = if (userType.value == "student") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Institute",
                    fontWeight = if (userType.value == "institute") FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.clickable { userType.value = "institute" },
                    color = if (userType.value == "institute") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            if (userType.value == "student") {
                StudentForm(
                    loading = false,
                    isCreateAccount = !showLoginForm.value,
                ) { email, password, userId ->
                    if (showLoginForm.value) {
                        viewModel.signIn(email, password) {
                            coroutineScope.launch(Dispatchers.IO) {
                                try {
                                    val response = apiService.getuserid(email)
                                    withContext(Dispatchers.Main) {
                                        navController.navigate("ReaderHomeScreen/${response.userId}")
                                    }
                                } catch (e: Exception) {
                                    // Handle error (e.g., show a toast or log it)
                                }
                            }
                        }
                    }
                    else {
                        viewModel.register(email, password) {
                            val userdata= emailuserid(
                                email=email,
                                userId=userId
                            )
                            coroutineScope.launch(Dispatchers.IO) {
                                try {
                                    val response =  apiService.Storeuserid(userdata)
                                } catch (e: Exception) {
                                }
                            }

                            navController.navigate("StudentRegistrationForm/$userId")
                        }
                    }
                }
            } else {
                InstituteForm(
                    loading = false,
                    isCreateAccount = !showLoginForm.value
                ) { email, password, userId ->
                    if (showLoginForm.value) {
                        viewModel.signIn(email, password) {
                            coroutineScope.launch(Dispatchers.IO) {
                                try {
                                    val response = apiService.getuserid(email)
                                    withContext(Dispatchers.Main) {
                                        navController.navigate("InstituteHomeScreen/${response.userId}")
                                    }
                                } catch (e: Exception) {
                                    // Handle error (e.g., show a toast or log it)
                                }
                            }
                        }
                    }

                    else {
                        viewModel.register(email, password) {
                            val userdata= emailuserid(
                                email=email,
                                userId=userId
                            )
                            coroutineScope.launch(Dispatchers.IO) {
                                try {
                                    val response =  apiService.Storeuserid(userdata)
                                } catch (e: Exception) {
                                }
                            }
                            navController.navigate("Instituteregistration/$userId")
                        }
                    }
                }
            }

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
fun StudentForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String, String?) -> Unit = { email, pwd, userId -> }
) {
    val userId = rememberSaveable { mutableStateOf("") }
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val userIdError = remember { mutableStateOf("") }
    val emailError = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf("") }
    val valid = remember(email.value, password.value, userId.value, isCreateAccount) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty() && (isCreateAccount && userId.value.trim().isNotEmpty() || !isCreateAccount)
    }
    val modifier = Modifier
        .height(350.dp)
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
        if (isCreateAccount) {
            InputField(
                valueState = userId,
                labelId = "User ID",
                enabled = true,
                isError = userIdError.value.isNotEmpty(),
                errorMessage = userIdError.value
            )
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
                onDone(email.value.trim(), password.value.trim(), if (isCreateAccount) userId.value.trim() else null)
            }
        )
        if (passwordError.value.isNotEmpty()) {
            Text(
                text = passwordError.value,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
    SubmitButton(
        textId = if (isCreateAccount) "Create Account" else "Login",
        loading = loading,
        validInputs = valid,
        onClick = {
            if (isCreateAccount && userId.value.trim().isEmpty()) {
                userIdError.value = "User ID cannot be empty"
            } else {
                userIdError.value = ""
            }
            if (valid) {
                onDone(email.value.trim(), password.value.trim(), if (isCreateAccount) userId.value.trim() else null)
                keyboardController?.hide()
            }
        }
    )
}



@Composable
fun InstituteForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String, String) -> Unit = { email, pwd, userId -> }
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val emailError = remember { mutableStateOf("") }
    val passwordError = remember { mutableStateOf("") }
    val userId = rememberSaveable { mutableStateOf("") } // State for user ID (only for registration)
    val userIdError = remember { mutableStateOf("") } // Error state for user ID (only for registration)
    val valid = remember(email.value, password.value, userId.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty() && (isCreateAccount && userId.value.trim().isNotEmpty() || !isCreateAccount)
    }
    val modifier = Modifier
        .height(if (isCreateAccount) 300.dp else 250.dp) // Adjusted height
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
            }
        )
        if (passwordError.value.isNotEmpty()) {
            Text(
                text = passwordError.value,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        if (isCreateAccount) {
            InputField(
                valueState = userId,
                labelId = "User ID",
                enabled = false,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                isError = userIdError.value.isNotEmpty(),
                errorMessage = userIdError.value
            )

            Button(
                onClick = {
                    userId.value = UUID.randomUUID().toString()
                    userIdError.value = ""
                },
                modifier = Modifier.padding(vertical = 10.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Generate User ID")
            }
        }
    }
    SubmitButton(
        textId = if (isCreateAccount) "Create Account" else "Login",
        loading = loading,
        validInputs = valid,
        onClick = {
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

            if (isCreateAccount && userId.value.trim().isEmpty()) {
                userIdError.value = "User ID cannot be empty"
            } else {
                userIdError.value = ""
            }

            if (emailError.value.isEmpty() && passwordError.value.isEmpty() && (isCreateAccount && userIdError.value.isEmpty() || !isCreateAccount)) {
                onDone(email.value.trim(), password.value.trim(), userId.value)
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
