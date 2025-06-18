package com.example.mediaexplorer.ui.ui.Generos

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mediaexplorer.R
import com.example.mediaexplorer.Screens.AppViewModelProvider
import com.example.mediaexplorer.data.Genero
import androidx.compose.foundation.lazy.items
import com.example.mediaexplorer.data.GeneroConPelicula


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstScreen(
    navController: NavController,
    viewModel: GeneroViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    LaunchedEffect(Unit) {
        viewModel.refreshGeneros()
    }

    var generosUiState by remember { mutableStateOf<GenerosUi>(GenerosUi.Loading) }

    var showAddDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var generoToDelete by remember { mutableStateOf<Genero?>(null) }

    Scaffold(
        containerColor = Color(0xFF141414),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.NuevaCategoria),
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.Button3),
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF141414),
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = Color(0xFFE50914),
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar")
            }
        }
    ) { paddingValues ->
        when (generosUiState) {
            is GenerosUi.Loading -> {
                Box(modifier = Modifier.padding(paddingValues).fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is GenerosUi.Error -> {
                Text(
                    text = (generosUiState as GenerosUi.Error).message,
                    color = Color.Red,
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                )
            }
            is GenerosUi.Success -> {
                val generos = (generosUiState as GenerosUi.Success).generos
                if (generos.isEmpty()) {
                    Text(
                        text = stringResource(R.string.button4),
                        color = Color.Red,
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    )
                } else {
                    LazyColumn(modifier = Modifier.padding(paddingValues)) {
                        items(generos, key = { it.id }) { genero ->
                            CategoryItem(
                                categoria = genero,
                                peliculasCount = 0,
                                onCategoryClick = { navController.navigate("second_page/${genero.id}") },
                                onDeleteClick = {
                                    generoToDelete = genero
                                    showDeleteDialog = true
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        var newGeneroName by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            title = { Text("Nuevo Género", color = Color.Black) },
            text = {
                TextField(
                    value = newGeneroName,
                    onValueChange = { newGeneroName = it },
                    label = { Text("Nombre del género") },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.Gray
                    )
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.addCategory(newGeneroName)
                        showAddDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE50914),
                        contentColor = Color.White
                    )
                ) {
                    Text("Crear")
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancelar", color = Color.Black)
                }
            },
            containerColor = Color.White
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirmar eliminación", color = Color.Black) },
            text = {
                Text(
                    "¿Estás seguro de que quieres eliminar el género '${generoToDelete?.genero}' y todas sus películas asociadas? Esta acción es irreversible.",
                    color = Color.Black
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        generoToDelete?.let { viewModel.deleteGeneroById(it.id) }
                        showDeleteDialog = false
                        generoToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE50914),
                        contentColor = Color.White
                    )
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    generoToDelete = null
                }) {
                    Text("Cancelar", color = Color.Black)
                }
            },
            containerColor = Color.White
        )
    }
}

@Composable
fun CategoryItem(
    categoria: Genero,
    peliculasCount: Int,
    onCategoryClick: (Genero) -> Unit,
    onDeleteClick: (Genero) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCategoryClick(categoria) },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1F1F1F),
            contentColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = categoria.genero,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
                Text(
                    text = "Películas: $peliculasCount",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
            IconButton(onClick = { onDeleteClick(categoria) }) {
                Icon(Icons.Filled.Delete, contentDescription = "", tint = Color.White)
            }
        }
    }
}
