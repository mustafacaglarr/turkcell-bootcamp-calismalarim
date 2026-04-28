package com.turkcell.bootcamp.libraryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turkcell.bootcamp.libraryapp.data.model.Profile
import com.turkcell.bootcamp.libraryapp.data.repository.AuthRepository
import com.turkcell.bootcamp.libraryapp.data.supabase.supabase
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Sistem bu 4Ã¼nden birinde olabilir.
sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val role: String) : AuthState()
    data class Error(val message: String) : AuthState()
}


class AuthViewModel : ViewModel()
{
    private val repository = AuthRepository()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState;

    private val _profile = MutableStateFlow<Profile?>(null)
    val profile: StateFlow<Profile?> = _profile;

    fun signIn(email: String, password: String)
    {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            repository
                .signIn(email, password)
                .onSuccess {
                    val userId = repository.getCurrentUserId(); // userId Geliyo mu?
                    if(userId != null)
                    {
                        val profile = repository.getProfile(userId); // profile geliyo mu?
                        _profile.value = profile; // doÄŸru set ediliyor mu?
                        _authState.value = AuthState.Success("student")
                    }else{
                        _authState.value = AuthState.Error("Profil bulunamadÄ±.")
                    }
                }
                .onFailure { ex -> _authState.value = AuthState.Error(ex.message ?: "GiriÅŸ baÅŸarÄ±sÄ±z") }
        }
    }

    fun signUp(
        email: String,
        password: String,
        fullName: String,
        studentNo: String?
    ) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            repository
                .signUp(email, password, fullName, studentNo)
                .onSuccess { result -> _authState.value = AuthState.Success("student") }
                .onFailure { ex -> _authState.value = AuthState.Error(ex.message ?: "KayÄ±t baÅŸarÄ±sÄ±z") }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle;
    }
}