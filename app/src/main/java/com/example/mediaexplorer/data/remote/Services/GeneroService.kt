package com.example.mediaexplorer.data.remote.Services

import com.example.mediaexplorer.data.Genero
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GeneroService {
    @GET("api/generos")
    suspend fun getGenero(): List<Genero>

    @POST("api/generos")
    suspend fun addGenero(@Body genero: Genero): Genero

    @DELETE("api/generos/{id}")
    suspend fun deleteGeneroById(@Path("id") id: Int): Genero

}