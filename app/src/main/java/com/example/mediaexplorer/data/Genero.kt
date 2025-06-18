package com.example.mediaexplorer.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "generos")
data class Genero(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val genero: String,
    val Icon: Int? = null
)

