package com.example.mediaexplorer.ui.ui.Generos

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mediaexplorer.R

import com.example.mediaexplorer.Screens.AppViewModelProvider
import com.example.mediaexplorer.Screens.CategoriesList
import com.example.mediaexplorer.Screens.SecondPage
import com.example.mediaexplorer.Screens.TerceraPage
import androidx.compose.foundation.lazy.grid.items


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: GeneroViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    LaunchedEffect(Unit) {
        viewModel.refreshGeneros()
    }

    var selected by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFF141414),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.SelectGen),
                        color = Color.White
                    )
                },
                actions = {
                    Button(
                        onClick = { navController.navigate(CategoriesList.toString()) },
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = Color(0xFFE50914),
                        )
                    ) {
                        Icon(Icons.Default.Settings, contentDescription = "Gestionar Categorías")
                        Text(
                            text = stringResource(R.string.Button1),
                            color = Color.White
                        )
                    }

                    Spacer(Modifier.size(8.dp))
                    Button(
                        onClick = { navController.navigate(TerceraPage(id = 0)) },
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = Color(0xFFE50914),
                        )
                    ) {
                        Icon(Icons.Default.List, contentDescription = "Añadir Película")
                        Text(
                            text = stringResource(R.string.Button2),
                            color = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF141414),
                    titleContentColor = Color.White
                )
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = viewModel.generosui) {
                is GenerosUi.Loading -> {
                    Text(text = "Cargando géneros...", color = Color.White, modifier = Modifier.padding(16.dp))
                }
                is GenerosUi.Error -> {
                    Text(text = "Error: ${state.message}", color = Color.White, modifier = Modifier.padding(16.dp))
                }
                is GenerosUi.Success -> {
                    if (state.generos.isEmpty()) {
                        Text(
                            text = stringResource(R.string.CategoriaNoDisponible),
                            color = Color.White,
                            modifier = Modifier.padding(16.dp)
                        )
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(20.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(state.generos, key = { it.id }) { genero ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            selected = genero.id
                                            navController.navigate(SecondPage(genero.id))
                                        },
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (selected == genero.id) Color(0xFFE50914) else Color(0xFF1F1F1F),
                                        contentColor = Color.White
                                    )
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 35.dp, horizontal = 20.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Image(
                                            painter = painterResource(id = genero.Icon ?: R.drawable.ic_launcher_background),
                                            contentDescription = genero.genero,
                                            modifier = Modifier.size(60.dp)
                                        )
                                        Spacer(Modifier.height(8.dp))
                                        Text(
                                            text = genero.genero,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
