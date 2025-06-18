package com.example.mediaexplorer.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "peliculas",
    foreignKeys = [
        ForeignKey(
            entity = Genero::class,
            parentColumns = ["id"],
            childColumns = ["categorizeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("categorizeId")]
)
data class Pelicula(
    @PrimaryKey(autoGenerate = true)
    val idPelicula: Int = 0,
    val nombre: String,
    val duracion: Int,
    val descripcion: String,
    val categorizeId: Int?,
)
