package com.example.mediaexplorer.ui.ui.Pelicula

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mediaexplorer.R
import com.example.mediaexplorer.Screens.AppViewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FourScreen(
    navController: NavController,
    id: Int,
    fourviewModel: FourPageViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    LaunchedEffect(id) {
        fourviewModel.MostrarPelicula(id)
    }

    val estado = fourviewModel.peliculamostrarUi

    Scaffold(
        containerColor = Color(0xFF141414),
        topBar = {
            TopAppBar(
                title = {
                    Button(
                        onClick = { navController.popBackStack() },
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = Color(0xFFe50914)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = stringResource(R.string.Button3))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF141414),
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        when (estado) {
            is PeliculaMostrarUI.Success -> {
                val pelicula = (estado as PeliculaMostrarUI.Success).pelicula
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 24.dp, vertical = 30.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = pelicula.nombre,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.Durac, pelicula.duracion),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.LightGray
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = pelicula.descripcion,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White,
                        lineHeight = 24.sp
                    )
                }
            }
            PeliculaMostrarUI.Loading -> {
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFFe50914))
                }
            }
            is PeliculaMostrarUI.Error -> {
                val errorMessage = (estado as PeliculaMostrarUI.Error).message
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}
