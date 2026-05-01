package com.turkcell.bootcamp.libraryapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.turkcell.bootcamp.libraryapp.ui.screen.RegisterScreen
import com.turkcell.bootcamp.libraryapp.ui.screen.HomeScreen
import com.turkcell.bootcamp.libraryapp.ui.screen.LoginScreen
import com.turkcell.bootcamp.libraryapp.ui.screen.SplashScreen
import com.turkcell.bootcamp.libraryapp.ui.viewmodel.AuthViewModel
import com.turkcell.bootcamp.libraryapp.ui.viewmodel.BookViewModel

@Composable
fun NavGraph(navController: NavHostController = rememberNavController()) {
    val authViewModel: AuthViewModel = viewModel()
    val bookViewModel: BookViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Splash.route)
    {

        composable(Screen.Splash.route) {
            SplashScreen(authViewModel,
                onAuthenticated = { role ->
                    navController.navigate(Screen.Homepage.route){
                        popUpTo(Screen.Splash.route) {inclusive=true}
                    }
                },
                onUnauthenticated = {
                    navController.navigate(Screen.Login.route)
                    {
                        popUpTo(Screen.Splash.route) {inclusive=true}
                    }
                })
        }

        composable(Screen.Login.route) { LoginScreen(
            onNavigateToRegister = { navController.navigate(Screen.Register.route) },
            onLoginSuccess = {role ->
                navController.navigate(Screen.Homepage.route) {
                    popUpTo(Screen.Login.route) {inclusive=true}

                }
            },
            authViewModel
        ) }
        composable(Screen.Register.route) { RegisterScreen(
            onNavigateToLogin = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Register.route) { inclusive = true }
                }
            },
            authViewModel
        ) }
        composable(Screen.Homepage.route) {
            HomeScreen(authViewModel, bookViewModel)
        }
    }
}
