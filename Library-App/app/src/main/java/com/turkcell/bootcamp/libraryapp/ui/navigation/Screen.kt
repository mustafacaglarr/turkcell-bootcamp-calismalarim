package com.turkcell.bootcamp.libraryapp.ui.navigation

// Sayfa routelarÄ±mÄ±n tanÄ±mÄ±.
sealed class Screen(val route: String)
{
    object Login : Screen("login")
    object Register : Screen("register")
    object Homepage : Screen("homepage")
}