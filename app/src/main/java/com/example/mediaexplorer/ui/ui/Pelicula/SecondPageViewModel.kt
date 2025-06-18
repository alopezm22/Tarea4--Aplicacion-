package com.example.mediaexplorer.ui.ui.Pelicula

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaexplorer.data.Pelicula
import com.example.mediaexplorer.data.remote.Services.PeliculaService
import kotlinx.coroutines.launch

sealed class PeliculaUI {
    data class Success(val pelicula: Pelicula) : PeliculaUI()
    data class Error(val message: String) : PeliculaUI()
    object Loading : PeliculaUI()
}

class SecondPageViewModel(
    private val peliculaService: PeliculaService
) : ViewModel() {

    var peliculaUi: PeliculaUI by mutableStateOf(PeliculaUI.Loading)
        private set

    internal fun getPeliculaById(id: Int){
        viewModelScope.launch {
            peliculaUi = PeliculaUI.Loading
            peliculaUi = try {
                val peli = peliculaService.getPeliculaById(id)
                PeliculaUI.Success(peli)
            } catch (e: Exception) {
                PeliculaUI.Error(e.message ?: "Error al cargar la película")
            }
        }
    }
    fun Actualizar (generoId: Int) {
        getPeliculaById(generoId)
    }
}