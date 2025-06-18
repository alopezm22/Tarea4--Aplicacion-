package com.example.mediaexplorer.data

import androidx.room.Embedded
import androidx.room.Relation

data class GeneroConPelicula(
    @Embedded val categorize: Genero,
    @Relation(
        parentColumn = "id",
        entityColumn = "categorizeId"
    )
    val peliculas: List<Pelicula>
)