package com.example.mediaexplorer.ui.ui.Pelicula

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaexplorer.data.Pelicula
import com.example.mediaexplorer.data.remote.Services.PeliculaService
import kotlinx.coroutines.launch

sealed class NuevaPeliculaUI {
    data class Success(val elements: List<Pelicula>) : NuevaPeliculaUI()
    data class Error(val message: String) : NuevaPeliculaUI()
    object Loading : NuevaPeliculaUI()
    object Idle : NuevaPeliculaUI()
}

class TerceraPageViewModel (private val peliculaService: PeliculaService) : ViewModel() {

    var nuevaPeliculaUI: NuevaPeliculaUI by mutableStateOf(NuevaPeliculaUI.Loading)
        private set

    fun NuevaPelicula (nombre: String, duracion: Int, descripcion: String, categoryId: Int){

        viewModelScope.launch {
            nuevaPeliculaUI = NuevaPeliculaUI.Loading
            try {
                peliculaService.addPelicula(Pelicula(0,nombre,duracion,descripcion, categoryId))
                val updatedList = peliculaService.getPelicula()
                nuevaPeliculaUI = NuevaPeliculaUI.Success(updatedList)
            } catch (e: Exception) {
                nuevaPeliculaUI = NuevaPeliculaUI.Error(e.message ?: "error")
            }
        }
    }
    fun ReseteoUI() {
        nuevaPeliculaUI = NuevaPeliculaUI.Idle
    }
}
