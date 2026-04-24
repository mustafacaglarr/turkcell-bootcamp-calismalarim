package com.turkcell.bootcamp.libraryapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.turkcell.bootcamp.libraryapp.ui.screen.BookListScreen
import com.turkcell.bootcamp.libraryapp.ui.screen.LoginScreen
import com.turkcell.bootcamp.libraryapp.ui.screen.RegisterScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(onLoginSuccess = { navController.navigate(Screen.BookList.route) }, onRegisterClick = { navController.navigate(Screen.Register.route) })
        }
        composable(Screen.Register.route) {
            RegisterScreen(onRegisterSuccess = { navController.popBackStack() }, onCancel = { navController.popBackStack() })
        }
        composable(Screen.BookList.route) {
            BookListScreen()
        }
    }
}
