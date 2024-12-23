package com.example.readerapp.Navigation

enum class ReaderScreens{
    SplashScreen,
    LoginScreen,
    CreateAccountScreen,
    ReaderHomeScreen,
    DonationPortal,
    DonationPortal2,
    UpdateScreen,
    ReaderStatsScreen,
    JobListScreen,
    AddJobScreen,
    ProfileScreen;
    companion object{
        fun fromRoute(route: String?):ReaderScreens
        =when(route?.substringBefore('/')){
            SplashScreen.name->SplashScreen
            LoginScreen.name->LoginScreen
            CreateAccountScreen.name->CreateAccountScreen
            ReaderHomeScreen.name->ReaderHomeScreen
            DonationPortal.name->DonationPortal
            DonationPortal2.name->DonationPortal2
            UpdateScreen.name->UpdateScreen
            ReaderStatsScreen.name->ReaderStatsScreen
            JobListScreen.name->JobListScreen
            AddJobScreen.name->AddJobScreen
            ProfileScreen.name->ProfileScreen
            null->ReaderHomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}