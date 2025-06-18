package com.example.mediaexplorer.data

import android.content.Context
import com.example.mediaexplorer.data.remote.ApiClient
import com.example.mediaexplorer.data.remote.Services.AuthService
import com.example.mediaexplorer.data.remote.Services.GeneroService
import com.example.mediaexplorer.data.remote.Services.PeliculaService

interface AppContainer {
    val authApiService: AuthService
    val generoApiService: GeneroService
    val peliculaApiService: PeliculaService
}

class AppDataContainer(private val context: Context) : AppContainer {

    override val authApiService: AuthService by lazy {
        ApiClient.create(context).create(AuthService::class.java)
    }

    override val generoApiService: GeneroService by lazy {
        ApiClient.create(context).create(GeneroService::class.java)
    }

    override val peliculaApiService: PeliculaService by lazy {
        ApiClient.create(context).create(PeliculaService::class.java)
    }
}
