package com.example.readerapp.Screen.Directory

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.readerapp.Screen.Login.LoginScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisplayNamesScreen(
    viewModel: LoginScreenViewModel = viewModel(),
    navController: NavHostController
) {
//    Scaffold(
//        topBar = {
//            TopAppBar(title = { Text("Directory") })
//        }
//    ) { paddingValues ->
////        DisplayNameList(viewModel, Modifier.padding(paddingValues))
//    }
}

//@Composable
//fun DisplayNameList(viewModel: LoginScreenViewModel, modifier: Modifier = Modifier) {
//    val displayNames by viewModel.displayNames.observeAsState(initial = emptyList())
//
//    LaunchedEffect(Unit) {
////        viewModel.fetchDisplayNames() // Fetch display names when the Composable loads
//    }
//
//    if (displayNames.isEmpty()) {
//        Text(
//            text = "No names to display",
//            modifier = modifier.padding(16.dp),
//            style = MaterialTheme.typography.bodyLarge
//        )
//    } else {
//        LazyColumn(modifier = modifier.padding(16.dp)) {
//            items(displayNames) { name ->
////                Text(
////                    text = name,
////                    style = MaterialTheme.typography.bodyLarge,
////                    modifier = Modifier.padding(8.dp)
////                )
////            }
//        }
//    }
//}
