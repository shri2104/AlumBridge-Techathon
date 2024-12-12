package com.example.readerapp.Navigation

enum class ReaderScreens{
    SplashScreen,
    LoginScreen,
    CreateAccountScreen,
    ReaderHomeScreen,
    SearchScreen,
    DetailScreen,
    UpdateScreen,
    ReaderStatsScreen,
    JobListScreen,
    AddJobScreen;
    companion object{
        fun fromRoute(route: String?):ReaderScreens
        =when(route?.substringBefore('/')){
            SplashScreen.name->SplashScreen
            LoginScreen.name->LoginScreen
            CreateAccountScreen.name->CreateAccountScreen
            ReaderHomeScreen.name->ReaderHomeScreen
            SearchScreen.name->SearchScreen
            DetailScreen.name->DetailScreen
            UpdateScreen.name->UpdateScreen
            ReaderStatsScreen.name->ReaderStatsScreen
            JobListScreen.name->JobListScreen
            AddJobScreen.name->AddJobScreen
            null->ReaderHomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}