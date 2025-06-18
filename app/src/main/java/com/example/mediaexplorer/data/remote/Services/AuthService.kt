package com.example.mediaexplorer.data.remote.Services

import com.example.mediaexplorer.model.LoginRequest
import com.example.mediaexplorer.model.LoginResponse
import com.example.mediaexplorer.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("api/profile")
    suspend fun getUser(): User

    @POST("logout")
    suspend fun logout()
}