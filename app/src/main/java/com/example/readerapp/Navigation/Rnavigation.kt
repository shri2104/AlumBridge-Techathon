package com.example.readerapp.Navigation


import EventReunionsScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.readerapp.viewmodel.JobViewModel
import com.example.readerapp.RHomeScreen
import com.example.readerapp.Screen.Directory.DisplayNamesScreen
import com.example.readerapp.Screen.EventsandReunion.PostedEventsScreen
import com.example.readerapp.Screen.Institute.InstituteDashBoard
import com.example.readerapp.Screen.Login.RLoginScreen
import com.example.readerapp.Screen.RSplashScreen
import com.example.readerapp.Screen.donationportal.BankDetailsScreen
import com.example.readerapp.Screen.donationportal.BankDetailsScreenForInstitute
import com.example.readerapp.Screen.donationportal.DonationInputScreen
import com.example.readerapp.Screen.donationportal.DonationSubmissionScreen
import com.example.readerapp.Screen.jobs.AddJobScreen
import com.example.readerapp.Screen.jobs.JobListScreen
import com.example.readerapp.Screen.profile.ProfileScreen
import com.example.readerapp.viewmodel.DonationViewModel
import com.example.readerapp.viewmodel.EventViewModel
import com.example.readerapp.viewmodel.ProfileViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RNavigation() {
    val navController = rememberNavController()
    val JobViewModel: JobViewModel = hiltViewModel()
    val ProfileViewModel: ProfileViewModel = hiltViewModel()
    val Donationviewmodel: DonationViewModel = hiltViewModel()
    val eventviewmodel: EventViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name) {
        composable(ReaderScreens.SplashScreen.name) {
            RSplashScreen(navController = navController)
        }
        composable(ReaderScreens.LoginScreen.name) {
            RLoginScreen(navController = navController)
        }
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
            BankDetailsScreen(navController = navController,
                donationViewModel = Donationviewmodel)
        }
        composable(ReaderScreens.DonationPortal2.name) {
            DonationSubmissionScreen(navController = navController)
        }
        composable(ReaderScreens.ProfileScreen.name) {
            ProfileScreen(navController = navController,viewModel = ProfileViewModel)
        }
        composable(ReaderScreens.InstituteHomeScreen.name) {
            InstituteDashBoard(navController = navController)
        }
        composable(ReaderScreens.EventsPostings.name) {
            EventReunionsScreen(
                navController = navController,
                eventViewModel =eventviewmodel
            )
        }
        composable(ReaderScreens.Directory.name) {
            DisplayNamesScreen(navController = navController)
        }
        composable(ReaderScreens.DonationInfoEntry.name) {
            DonationInputScreen(
                navController = navController,
                donationViewModel = Donationviewmodel
            )
        }
        composable(ReaderScreens.DonationInfo.name) {
            BankDetailsScreenForInstitute(navController = navController,
                donationViewModel = Donationviewmodel)
        }
        composable(ReaderScreens.postedEvents.name) {
            PostedEventsScreen(
                navController = navController,
                eventViewModel =eventviewmodel
            )
        }
    }
}
