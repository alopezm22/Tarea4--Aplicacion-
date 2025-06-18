package com.example.mediaexplorer.Screens

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

// IMPORTACIONES CRUCIALES PARA RESOLVER 'Unresolved reference: AppContainer' y 'AppDataContainer'
import com.example.mediaexplorer.data.AppContainer     // <--- ¡AÑADE ESTA LÍNEA!
// No es estrictamente necesario importar AppDataContainer aquí a menos que se use directamente.
// Pero la importación de AppContainer es vital.
// import com.example.mediaexplorer.data.AppDataContainer // Puedes añadirla si el IDE la pide más adelante.

// Importa tu clase Application personalizada (MediaExplorer)
// Si MediaExplorer está en el mismo paquete (Screens), no necesitas esta importación explícita.
// Si no lo estuviera, necesitarías: import com.example.mediaexplorer.MediaExplorer

// Importaciones de tus ViewModels (estas ya estaban correctas)
import com.example.mediaexplorer.ui.ui.Generos.GeneroViewModel
// import com.example.mediaexplorer.ui.ui.Generos.HomeScreen // No usada en este archivo, puedes quitarla si está
import com.example.mediaexplorer.ui.ui.Pelicula.FourPageViewModel
import com.example.mediaexplorer.ui.ui.Pelicula.SecondPageViewModel
import com.example.mediaexplorer.ui.ui.Pelicula.TerceraPageViewModel
import com.example.mediaexplorer.ui.ui.iniciosecion.AuthViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer { GeneroViewModel(App().container.generoApiService) }

        initializer { TerceraPageViewModel(App().container.peliculaApiService) }

        initializer { FourPageViewModel(App().container.peliculaApiService) }

        initializer { SecondPageViewModel(App().container.peliculaApiService) }

        initializer { AuthViewModel(App().container.authApiService, App().applicationContext) } // Usando applicationContext
    }
}

fun CreationExtras.App(): MediaExplorer =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as MediaExplorer)