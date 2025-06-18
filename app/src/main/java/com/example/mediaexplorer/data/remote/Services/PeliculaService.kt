package com.example.mediaexplorer.data.remote.Services

import com.example.mediaexplorer.data.Pelicula
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PeliculaService {

    @GET("api/peliculas")
    suspend fun getPeliculasdeGenero(@Query("genero_id") categoryId: Int): List<Pelicula>

    @GET("api/elements/{id}")
    suspend fun getPeliculaById(@Path("id") id: Int): Pelicula

    @GET("api/elements")
    suspend fun getPelicula(): List<Pelicula>

    @POST("api/elements")
    suspend fun addPelicula(@Body genero: Pelicula): Pelicula

    @DELETE("api/elements/{id}")
    suspend fun deletePeliculaById(@Path("id") id: Int): Pelicula


}