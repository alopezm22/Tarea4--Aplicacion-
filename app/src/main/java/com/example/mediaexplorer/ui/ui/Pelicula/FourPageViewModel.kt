package com.example.mediaexplorer.ui.ui.Pelicula

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaexplorer.data.Pelicula
import com.example.mediaexplorer.data.remote.Services.PeliculaService
import kotlinx.coroutines.launch

sealed class PeliculaMostrarUI {
    data class Success(val pelicula: Pelicula) : PeliculaMostrarUI()
    data class Error(val message: String) : PeliculaMostrarUI()
    object Loading : PeliculaMostrarUI()
}

class FourPageViewModel(
    private val peliculaServicee: PeliculaService
) : ViewModel() {

    var peliculamostrarUi: PeliculaMostrarUI by mutableStateOf(PeliculaMostrarUI.Loading)
        private set

     fun MostrarPelicula (id: Int) {
        viewModelScope.launch {
            peliculamostrarUi = PeliculaMostrarUI.Loading
            peliculamostrarUi = try {
                val peli = peliculaServicee.getPeliculaById(id)
                PeliculaMostrarUI.Success(peli)
            } catch (e: Exception) {
                PeliculaMostrarUI.Error(e.message ?: "error")
            }
        }
    }
}
