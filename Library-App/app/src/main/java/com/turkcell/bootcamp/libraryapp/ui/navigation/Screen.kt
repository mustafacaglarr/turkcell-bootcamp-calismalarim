package com.turkcell.bootcamp.libraryapp.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object BookList : Screen("book_list")
}
