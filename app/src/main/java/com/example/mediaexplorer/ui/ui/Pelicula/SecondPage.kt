package com.example.mediaexplorer.ui.ui.Pelicula

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mediaexplorer.Screens.AppViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondScreen(
    navController: NavController,
    categoriaId: Int,
    secondViewModel: SecondPageViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    LaunchedEffect(categoriaId) {
        secondViewModel.Actualizar(categoriaId)
    }

    val uiState = secondViewModel.peliculaUi

    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFF141414),
        contentColor = Color.White,
        topBar = {
            LargeTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Color(0xFF141414)),
                title = {
                    when (uiState) {
                        is PeliculaUI.Loading -> {
                            Text(text = "Cargando película...")
                        }
                        is PeliculaUI.Error -> {
                            Text(text = "Error: ${uiState.message}")
                        }
                        is PeliculaUI.Success -> {
                            Text(text = "Película: ${uiState.pelicula.nombre}", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                        }
                        else -> {}
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { navController.navigate("home") },
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFe50914), contentColor = Color.White)
                    ) {
                        Text("Volver a Inicio")
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        when (uiState) {
            is PeliculaUI.Loading -> {
                Text(text = "Cargando película...", modifier = Modifier.padding(innerPadding))
            }
            is PeliculaUI.Error -> {
                Text(text = "Error: ${uiState.message}", modifier = Modifier.padding(innerPadding))
            }
            is PeliculaUI.Success -> {
                val pelicula = uiState.pelicula
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .verticalScroll(scrollState)
                ) {
                    Text(
                        text = pelicula.nombre,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(
                        text = "Duración: ${pelicula.duracion} minutos",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            else -> {}
        }
    }
}

