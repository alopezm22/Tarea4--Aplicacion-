package com.example.mediaexplorer.ui.ui.iniciosecion

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaexplorer.data.remote.Services.AuthService
import com.example.mediaexplorer.data.remote.TokenManager
import com.example.mediaexplorer.model.LoginRequest
import kotlinx.coroutines.launch

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class loggedIn(var logged: Boolean) : AuthUiState()
    data class Success(val token: String) : AuthUiState()
    data class Error(val message: String) : AuthUiState()

}

class AuthViewModel(
    private val authService: AuthService,
    private val context: Context
) : ViewModel() {

    var authState: AuthUiState by mutableStateOf(AuthUiState.Idle)
        private set

    init {
        isLoggedIn()
    }
    fun login(email: String, password: String) {
        viewModelScope.launch {
            authState = AuthUiState.Loading
            try {
                val response = authService.login(LoginRequest(email, password))
                TokenManager.saveToken(context, response.token)
                authState = AuthUiState.Success(response.token)
            } catch (e: Exception) {
                authState = AuthUiState.Error(e.message ?: "Login failed")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                authService.logout()
                TokenManager.clearToken(context)
                authState = AuthUiState.Idle
            } catch (_: Exception) {}
        }
    }

    private fun isLoggedIn() {
        viewModelScope.launch {
            authState = AuthUiState.Loading
            try {
                val token = TokenManager.getToken(context)
                if (token != null) {
                    // Opción rápida: Confiar en el token local ( funciona para login persistente )
                    authState = AuthUiState.loggedIn(true)

                    // Opción segura: Validar token con el backend
                    // val response = authService.getUser() // Llamada a /profile
                    // authState = AuthUiState.loggedIn(true)
                } else {
                    authState = AuthUiState.loggedIn(false)
                }
            } catch (e: Exception) {
                authState = AuthUiState.loggedIn(false)
            }
        }
    }
}
