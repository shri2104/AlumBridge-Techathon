package com.example.readerapp.Navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.readerapp.viewmodel.JobViewModel
import com.example.readerapp.RHomeScreen

import com.example.readerapp.Screen.Login.RLoginScreen
import com.example.readerapp.Screen.RSplashScreen
import com.example.readerapp.Screen.donationportal.BankDetailsScreen

import com.example.readerapp.Screen.donationportal.DonationSubmissionScreen
import com.example.readerapp.Screen.jobs.AddJobScreen
import com.example.readerapp.Screen.jobs.JobListScreen
import com.example.readerapp.Screen.profile.ProfileScreen

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RNavigation(){
    val navController= rememberNavController()
    val viewModel: JobViewModel = hiltViewModel()
    NavHost(navController=navController,startDestination = ReaderScreens.SplashScreen.name){
        composable(ReaderScreens.SplashScreen.name){
            RSplashScreen(navController=navController)
        }
        composable(ReaderScreens.LoginScreen.name){
            RLoginScreen(navController=navController)
        }
        composable(ReaderScreens.ReaderHomeScreen.name){
            RHomeScreen(navController=navController)
        }
        composable(ReaderScreens.AddJobScreen.name){
            AddJobScreen(navController) { viewModel.addJob(it) }
        }
        composable(ReaderScreens.JobListScreen.name) {
            JobListScreen(navController = navController, viewModel)
        }
        composable(ReaderScreens.DonationPortal.name) {
            BankDetailsScreen(navController = navController)
        }
        composable(ReaderScreens.DonationPortal2.name) {
            DonationSubmissionScreen(navController = navController)
        }
        composable(ReaderScreens.ProfileScreen.name){
            ProfileScreen(navController = navController)
        }
    }
}