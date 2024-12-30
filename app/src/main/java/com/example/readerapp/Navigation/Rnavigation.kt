package com.example.readerapp.Navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.readerapp.viewmodel.JobViewModel
import com.example.readerapp.RHomeScreen
import com.example.readerapp.Screen.EventsandReunion.EventReunionsScreen
import com.example.readerapp.Screen.Institute.InstituteDashBoard
import com.example.readerapp.Screen.Login.RLoginScreen
import com.example.readerapp.Screen.RSplashScreen
import com.example.readerapp.Screen.donationportal.BankDetailsScreen
import com.example.readerapp.Screen.donationportal.DonationSubmissionScreen
import com.example.readerapp.Screen.jobs.AddJobScreen
import com.example.readerapp.Screen.jobs.JobListScreen
import com.example.readerapp.Screen.profile.ProfileScreen
import com.example.readerapp.viewmodel.ProfileViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RNavigation() {
    val navController = rememberNavController()
    val JobViewModel: JobViewModel = hiltViewModel()
    val ProfileViewModel: ProfileViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name) {
        composable(ReaderScreens.SplashScreen.name) {
            RSplashScreen(navController = navController)
        }
        composable(ReaderScreens.LoginScreen.name) {
            RLoginScreen(navController = navController)
        }
//        composable(ReaderScreens.InstituteLoginScreen.name) {
//            InstituteLoginScreen(navController = navController)
//        }
        composable(ReaderScreens.ReaderHomeScreen.name) {
            RHomeScreen(navController = navController)
        }
        composable(ReaderScreens.AddJobScreen.name) {
            AddJobScreen(navController) { JobViewModel.addJob(it) }
        }
        composable(ReaderScreens.JobListScreen.name) {
            JobListScreen(navController = navController, JobViewModel)
        }
        composable(ReaderScreens.DonationPortal.name) {
            BankDetailsScreen(navController = navController)
        }
        composable(ReaderScreens.DonationPortal2.name) {
            DonationSubmissionScreen(navController = navController)
        }
        composable(ReaderScreens.ProfileScreen.name) {
            ProfileScreen(navController = navController,viewModel = ProfileViewModel)
        }
//        composable(ReaderScreens.LoginSelectionScreen.name) {
//            LoginSelectionScreen(navController = navController)
//        }
        composable(ReaderScreens.InstituteHomeScreen.name) {
            InstituteDashBoard(navController = navController)
        }
        composable(ReaderScreens.EventsPostings.name) {
            EventReunionsScreen(navController = navController)
        }
//        composable("RegisterScreen/{userType}") { backStackEntry ->
//            val userType = backStackEntry.arguments?.getString("userType") ?: "student"
//            RegisterScreen(navController = navController, userType = userType)
//        }
    }
}
