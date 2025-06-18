package com.example.mediaexplorer.ui.ui.iniciosecion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mediaexplorer.Screens.AppViewModelProvider
import com.example.mediaexplorer.Screens.Home
import com.example.mediaexplorer.Screens.Login
import com.example.mediaexplorer.Screens.Registrer
import com.example.mediaexplorer.ui.ui.theme.PurpleGrey80


@Composable
fun LoginScreen(
    viewModel: AuthViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navController: NavHostController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var error by remember { mutableStateOf(false) }
    var error2 by remember { mutableStateOf(false) }

    val authState = viewModel.authState

    LaunchedEffect(authState) {
        if (authState is AuthUiState.Success) {
            navController.navigate(Home) {
                popUpTo(Login) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp)
            .padding(top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Mi Aplicación",
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Iniciar Sesión",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            leadingIcon = {

            },
            label = { Text("Correo Electrónico") },
            placeholder = { Text("ejemplo@dominio.com") },
            isError = error,
            supportingText = {
                if (error) Text("Ingresa tu correo electrónico")
            },
            minLines = 1,
            maxLines = 1,
            textStyle = TextStyle(color = Color.Black)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            leadingIcon = {

            },
            label = { Text("Contraseña") },
            placeholder = { Text("Ingresa tu contraseña") },
            isError = error2,
            supportingText = {
                if (error2) Text("Mínimo 6 caracteres")
            },
            minLines = 1,
            maxLines = 1,
            textStyle = TextStyle(color = Color.Black)
        )

        Button(
            onClick = {
                error = email.isBlank()
                error2 = password.isBlank()

                if (!error && !error2) {
                    viewModel.login(email, password)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text("Entrar")
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("¿No tienes cuenta?", fontSize = 16.sp)
            TextButton(onClick = { navController.navigate(Registrer) }) {
                Text("Regístrate aquí", color = PurpleGrey80, fontSize = 16.sp) // Asegúrate de que primaryLight esté disponible
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        when (authState) {
            is AuthUiState.Loading -> {
                CircularProgressIndicator()
            }
            is AuthUiState.Success -> {
                Text(
                    text = "Autenticación exitosa",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            is AuthUiState.Error -> {
                Text(
                    text = authState.message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            else -> {}
        }
    }
}