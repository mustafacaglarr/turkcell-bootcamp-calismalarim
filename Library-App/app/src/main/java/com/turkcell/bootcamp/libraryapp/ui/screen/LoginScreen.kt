package com.turkcell.bootcamp.libraryapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.turkcell.bootcamp.libraryapp.ui.viewmodel.AuthState
import com.turkcell.bootcamp.libraryapp.ui.viewmodel.AuthViewModel
import io.github.jan.supabase.auth.Auth


// TODO: KayÄ±t ol sayfasÄ± tasarlamak.
@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: (role:String) -> Unit,
    authViewModel: AuthViewModel
) {
    val authState by authViewModel.authState.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    // YalnÄ±zca authState deÄŸiÅŸirse Ã§alÄ±ÅŸ, tÃ¼m recompositionlarda deÄŸil..
    LaunchedEffect(authState) {
        if(authState is AuthState.Success)
        {
            onLoginSuccess((authState as AuthState.Success).role)
            authViewModel.resetState()
        }
    }


    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("KÃ¼tÃ¼phane Sistemi")
        Spacer(modifier =  Modifier.height(8.dp))
        Text("GiriÅŸ Yap")
        OutlinedTextField(
            enabled = authState !is AuthState.Loading,
            modifier = Modifier.fillMaxWidth(),
            value=email,
            label = {Text("E-posta")},
            onValueChange = {value -> email = value},
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            enabled = authState !is AuthState.Loading,
            modifier = Modifier.fillMaxWidth(),
            value=password,
            label = {Text("Åifre")},
            onValueChange = {value -> password = value},
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(10.dp))

        if(authState is AuthState.Loading)
        {
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                CircularProgressIndicator(modifier=Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary)
            }
        }else {
            Button(onClick = {
                authViewModel.signIn(email, password)
            }, modifier = Modifier.fillMaxWidth()) {
                Text("GiriÅŸ Yap")
            }
        }

        TextButton(onClick = {
            onNavigateToRegister()
        },) {
            Text("HesabÄ±nÄ±z yok mu? KayÄ±t Ol")
        }


        if(authState is AuthState.Success)
            Text("GiriÅŸ YapÄ±ldÄ±")
        else if(authState is AuthState.Error)
            Text((authState as AuthState.Error).message)
    }
}