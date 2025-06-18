package com.example.mediaexplorer.ui.ui.Generos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediaexplorer.data.Genero
import com.example.mediaexplorer.data.remote.Services.GeneroService

import kotlinx.coroutines.launch


sealed class GenerosUi {
    data class Success(val generos: List<Genero>) : GenerosUi()
    data class Error(val message: String) : GenerosUi()
    object Loading : GenerosUi()
}

class GeneroViewModel(private val generoService: GeneroService) : ViewModel() {

    var generosui: GenerosUi by mutableStateOf(GenerosUi.Loading)
        private set

    init {
        viewModelScope.launch {
            generosui = GenerosUi.Loading
            generosui = try {
                val listGeneros = generoService.getGenero()
                GenerosUi.Success(listGeneros)
            } catch (e: Exception) {
                GenerosUi.Error(e.message ?: "Error desconocido al cargar géneros")
            }
        }
    }

    fun addCategory(name: String) {
        viewModelScope.launch {
            generosui = GenerosUi.Loading
            try {
                generoService.addGenero(Genero(0, name, null))
                viewModelScope.launch {
                    generosui = GenerosUi.Loading
                    generosui = try {
                        val listGeneros = generoService.getGenero()
                        GenerosUi.Success(listGeneros)
                    } catch (e: Exception) {
                        GenerosUi.Error(e.message ?: "Error desconocido al cargar géneros")
                    }
                }
            } catch (e: Exception) {
                generosui = GenerosUi.Error(e.message ?: "Error al agregar género")
            }
        }
    }

    fun deleteGeneroById(id: Int) {
        viewModelScope.launch {
            generosui = GenerosUi.Loading
            try {
                generoService.deleteGeneroById(id)
                viewModelScope.launch {
                    generosui = GenerosUi.Loading
                    generosui = try {
                        val listGeneros = generoService.getGenero()
                        GenerosUi.Success(listGeneros)
                    } catch (e: Exception) {
                        GenerosUi.Error(e.message ?: "Error desconocido al cargar géneros")
                    }
                }
            } catch (e: Exception) {
                generosui = GenerosUi.Error(e.message ?: "Error al eliminar género")
            }
        }
    }

    fun refreshGeneros() {
        viewModelScope.launch {
            generosui = GenerosUi.Loading
            generosui = try {
                val listGeneros = generoService.getGenero()
                GenerosUi.Success(listGeneros)
            } catch (e: Exception) {
                GenerosUi.Error(e.message ?: "Error desconocido al cargar géneros")
            }
        }
    }
}
