package com.turkcell.bootcamp.libraryapp.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.turkcell.bootcamp.libraryapp.data.model.User
import com.turkcell.bootcamp.libraryapp.data.repository.UserRepository

class AuthViewModel : ViewModel() {
    private val repository = UserRepository()

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var name by mutableStateOf("")
        private set
    var loginError by mutableStateOf<String?>(null)
    var registrationError by mutableStateOf<String?>(null)

    fun updateEmail(value: String) {
        email = value
    }

    fun updatePassword(value: String) {
        password = value
    }

    fun updateName(value: String) {
        name = value
    }

    fun login(onSuccess: () -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            loginError = "E-posta ve parola gereklidir"
            return
        }

        val user = repository.authenticate(email, password)
        if (user != null) {
            loginError = null
            onSuccess()
        } else {
            loginError = "Giriş bilgileri yanlış"
        }
    }

    fun register(onSuccess: () -> Unit) {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            registrationError = "Lütfen tüm alanları doldurun"
            return
        }

        val result = repository.register(User(name, email, password))
        if (result) {
            registrationError = null
            onSuccess()
        } else {
            registrationError = "Bu e-posta zaten kayıtlı"
        }
    }
}
