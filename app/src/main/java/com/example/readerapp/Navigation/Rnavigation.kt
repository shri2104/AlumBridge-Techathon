package com.example.readerapp.Navigation


import EventReunionsScreen
import JobListScreen
import ProfileFormScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.readerapp.Donationdata3.StudentDonationViewModel
import com.example.readerapp.viewmodel.JobViewModel
import com.example.readerapp.RHomeScreen
import com.example.readerapp.Retrofit.ApiService
import com.example.readerapp.Screen.Directory.DisplayNamesScreen
import com.example.readerapp.Screen.EventsandReunion.EventDataForm
import com.example.readerapp.Screen.EventsandReunion.EventListScreen
import com.example.readerapp.Screen.EventsandReunion.PostedEventsScreen
import com.example.readerapp.Screen.Institute.InstituteDashBoard
import com.example.readerapp.Screen.Login.InstituteRegistrationForm
import com.example.readerapp.Screen.RSplashScreen
import com.example.readerapp.Screen.donationportal.BankDetailsScreen
import com.example.readerapp.Screen.donationportal.BankDetailsScreenForInstitute
import com.example.readerapp.Screen.donationportal.DonationInputScreen
import com.example.readerapp.Screen.donationportal.DonationListScreen
import com.example.readerapp.Screen.donationportal.DonationListScreen2
import com.example.readerapp.Screen.donationportal.DonationSubmissionScreen
import com.example.readerapp.Screen.donationportal.StudentDonationListScreen
import com.example.readerapp.Screen.donationportal.TDonationInputScreen
import com.example.readerapp.Screen.donationportal.ThankYouScreen
import com.example.readerapp.Screen.jobs.AddJobScreen
import com.example.readerapp.Screen.profile.Instituteprofilepage
import com.example.readerapp.donationdata.TotalDonationViewModel
import com.example.readerapp.viewmodel.DonationViewModel
import com.example.readerapp.viewmodel.EventViewModel
import com.example.readerapp.viewmodel.ProfileViewModel
import com.examplepackage.RLoginScreen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RNavigation(apiService: ApiService) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val JobViewModel: JobViewModel = hiltViewModel()
    val ProfileViewModel: ProfileViewModel = hiltViewModel()
    val Donationviewmodel: DonationViewModel = hiltViewModel()
    val eventviewmodel: EventViewModel = hiltViewModel()
    val totalDonationViewModel: TotalDonationViewModel = hiltViewModel()
    val studentdonationviewmodel: StudentDonationViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name) {
        composable(ReaderScreens.SplashScreen.name) {
            RSplashScreen(navController = navController)
        }
        composable(ReaderScreens.LoginScreen.name) {
            RLoginScreen(navController = navController,apiService)
        }
        composable(ReaderScreens.ReaderHomeScreen.name) {
            RHomeScreen(
                navController = navController,
                jobViewModel = JobViewModel,
                totalDonationViewModel =totalDonationViewModel
            )
        }
        

        composable("postedEvent"){
            EventListScreen(apiService)
        }
        composable("ProfileS"){
            ProfileFormScreen(apiService, navController)
        }
        composable(ReaderScreens.AddJobScreen.name) {
            AddJobScreen(navController = navController,apiService)
        }
        composable(ReaderScreens.JobListScreen.name) {
            JobListScreen(navController = navController,apiService,JobViewModel)
        }
        composable(ReaderScreens.DonationPortal.name) {
            BankDetailsScreen(navController = navController,
                donationViewModel = Donationviewmodel,apiService)
        }
        composable(ReaderScreens.DonationPortal2.name) {
            DonationSubmissionScreen(
                navController = navController,
                donationViewModel = studentdonationviewmodel,
                context = context
            )
        }
        composable(ReaderScreens.ProfileScreen.name) {
            ProfileFormScreen(
                navController = navController,
                apiService = apiService,
            )
        }
        composable("InstituteHomeScreen/{userId}") {backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            InstituteDashBoard(
                userId = userId,
                navController = navController,
                jobViewModel = JobViewModel,
                totalDonationViewModel = totalDonationViewModel

            )
        }
        composable("Instituteprfilepage/{userId}") {backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            Instituteprofilepage(
                userId = userId,
                navController = navController,
                apiService = apiService
            )
        }
        composable("Instituteregistration/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            InstituteRegistrationForm(
                userId = userId,
                navController = navController,
                apiService = apiService
            )
        }
        composable("Eventpost/{userId}") {backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            EventDataForm(
                navController = navController,
                apiService,
                userId = userId
            )
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
                donationViewModel = Donationviewmodel,
                apiService = apiService
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
        composable(ReaderScreens.totalDonation.name) {
            TDonationInputScreen(
                navController = navController,
                totalDonationViewModel = totalDonationViewModel,
                apiservice = apiService
            )
        }
        composable(ReaderScreens.DonationList.name) {
            DonationListScreen(
                navController = navController,
                totalDonationViewModel =totalDonationViewModel
            )
        }
        composable(ReaderScreens.DonationListforstudent.name) {
            DonationListScreen2(
                navController = navController,
                totalDonationViewModel =totalDonationViewModel
            )
        }
        composable(ReaderScreens.Thankyouscreen.name) {
            ThankYouScreen(navController = navController)
        }
        composable(ReaderScreens.RecievedDonations.name) {
            StudentDonationListScreen(
                navController = navController,
                donationViewModel = studentdonationviewmodel,

                )
        }
    }
}
