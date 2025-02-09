import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.readerapp.Retrofit.ApiService
import com.example.readerapp.Retrofit.ProfileData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color
import com.example.readerapp.Navigation.ReaderScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileFormScreen(apiService: ApiService, navController: NavHostController) {

    var name by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }
    var location by remember { mutableStateOf(TextFieldValue("")) }
    var batch by remember { mutableStateOf(TextFieldValue("")) }
    var currentWorkStatus by remember { mutableStateOf(TextFieldValue("")) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile", style = MaterialTheme.typography.headlineMedium) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = "Profile Icon")
                    }
                }
            )
        },
        bottomBar = {
            BottomInstituteNavigationBar(navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Custom styled TextFields using Card and TextField
            CustomTextField(value = name, onValueChange = { name = it }, label = "Name")
            CustomTextField(value = email, onValueChange = { email = it }, label = "Email")
            CustomTextField(value = phoneNumber, onValueChange = { phoneNumber = it }, label = "Phone Number")
            CustomTextField(value = location, onValueChange = { location = it }, label = "Location")
            CustomTextField(value = batch, onValueChange = { batch = it }, label = "Batch")
            CustomTextField(value = currentWorkStatus, onValueChange = { currentWorkStatus = it }, label = "Current Work Status")

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val profileData = ProfileData(
                        Name = name.text,
                        Email = email.text,
                        Phonenumber = phoneNumber.text,
                        Location = location.text,
                        Batch = batch.text,
                        CurrentworkStatus = currentWorkStatus.text
                    )
                    coroutineScope.launch(Dispatchers.IO) {
                        try {
                            val response = apiService.StoreProfiledata(profileData)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }
        }
    }
}

@Composable
fun CustomTextField(value: TextFieldValue, onValueChange: (TextFieldValue) -> Unit, label: String) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = "Enter $label") },
            label = { Text(text = label) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Blue,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
    }
}

@Composable
fun BottomInstituteNavigationBar(navController: NavHostController) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.9f),
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(ReaderScreens.InstituteHomeScreen.name) },
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("DonationInfo") },
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Donation Portal") },
            label = { Text("Donations") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("JobListScreen") },
            icon = { Icon(Icons.Filled.Work, contentDescription = "Job Postings") },
            label = { Text("Jobs") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("Directory") },
            icon = { Icon(Icons.Filled.Group, contentDescription = "Alumni Directory") },
            label = { Text("Directory") }
        )
    }
}
