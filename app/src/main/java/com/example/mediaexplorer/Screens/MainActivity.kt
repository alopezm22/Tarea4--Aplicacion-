package com.example.mediaexplorer.Screens // Mantiene 'Screens' con 'S' mayúscula, SIN el comentario de minúsculas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mediaexplorer.ui.ui.theme.MediaExplorerTheme

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MediaExplorerTheme {
                Navigation()
            }
        }
    }
}