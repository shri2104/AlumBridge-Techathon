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
import com.example.readerapp.Screen.Directory.DirectoryListScreen
import com.example.readerapp.Screen.EventsandReunion.EventDataForm
import com.example.readerapp.Screen.EventsandReunion.EventDetailsScreen
import com.example.readerapp.Screen.EventsandReunion.EventListScreen
import com.example.readerapp.Screen.Institute.InstituteDashBoard
import com.example.readerapp.Screen.Login.InstituteRegistrationForm
import com.example.readerapp.Screen.Login.StudentRegistrationForm
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
        composable("ReaderHomeScreen/{userId}") {backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            RHomeScreen(
                userId = userId,
                navController = navController,
                jobViewModel = JobViewModel,
                totalDonationViewModel = totalDonationViewModel,
                apiService = apiService
            )
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
        composable("InstituteHomeScreen/{userId}") {backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            InstituteDashBoard(
                userId = userId,
                navController = navController,
                jobViewModel = JobViewModel,
                totalDonationViewModel = totalDonationViewModel,
                apiService = apiService
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
        composable("StudentRegistrationForm/{userId}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            StudentRegistrationForm(
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
        composable("StudentProfile/{userId}") {backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            ProfileFormScreen(
                navController = navController,
                apiService = apiService,
                userId = userId
            )
        }

        composable("DonationinfoEntry/{userId}") {backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            DonationInputScreen(
                navController = navController,
                donationViewModel = Donationviewmodel,
                apiService = apiService,
                userId = userId
            )
        }

        composable("TotalDonation/{userId}") {backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            TDonationInputScreen(
                navController = navController,
                totalDonationViewModel = totalDonationViewModel,
                apiservice = apiService,
                userId = userId
            )
        }

        composable("Donationdashboard/{userId}") {backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            BankDetailsScreenForInstitute(
                navController = navController,
                donationViewModel = Donationviewmodel,
                apiservice = apiService,
                userId = userId
            )
        }
        composable("AddJobScreen/{userId}") {backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            AddJobScreen(
                navController = navController,
                apiService,
                userId = userId
            )
        }
        composable("JobListScreen/{userId}") {backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            JobListScreen(
                navController = navController,
                apiService,
                JobViewModel,
                userId
            )
        }
        composable("postedEvent/{userId}") {backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            EventListScreen(
                apiService,
                userId = userId,
                navController = navController,
            )
        }
        composable(
            route = "eventDetailScreen/{userId}/{Headline}/{Description}/{Dates}/{Location}/{Forms}/{EventType}"
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            val headline = backStackEntry.arguments?.getString("Headline") ?: ""
            val description = backStackEntry.arguments?.getString("Description") ?: ""
            val dates = backStackEntry.arguments?.getString("Dates") ?: ""
            val location = backStackEntry.arguments?.getString("Location") ?: ""
            val forms = backStackEntry.arguments?.getString("Forms") ?: ""
            val eventType = backStackEntry.arguments?.getString("EventType") ?: ""

            EventDetailsScreen(
                navController = navController,
                userId = userId,
                headline = headline,
                description = description,
                dates = dates,
                location = location,
                forms = forms,
                eventType = eventType
            )
        }
        composable("DonationList/{userId}") {backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            DonationListScreen(
                navController = navController,
                apiService,
                userId
            )
        }
        composable("Directory/{userId}") {backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            DirectoryListScreen(
                navController = navController,
                apiService,
                userId
            )
        }


        composable(ReaderScreens.EventsPostings.name) {
            EventReunionsScreen(
                navController = navController,
                eventViewModel =eventviewmodel
            )
        }
//        composable(ReaderScreens.postedEvents.name) {
//            PostedEventsScreen(
//                navController = navController,
//                eventViewModel =eventviewmodel
//            )
//        }

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
