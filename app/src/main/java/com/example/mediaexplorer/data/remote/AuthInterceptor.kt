package com.example.mediaexplorer.data.remote

import android.content.Context
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import java.io.IOException
import java.net.SocketTimeoutException

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            TokenManager.getToken(context)
        }

        val requestBuilder = chain.request().newBuilder()
        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return try {
            chain.proceed(requestBuilder.build())
        } catch (e: SocketTimeoutException) {
            createErrorResponse(chain, "No se pudo conectar al servidor (timeout)")
        } catch (e: IOException) {
            createErrorResponse(chain, "No se pudo conectar al servidor (error de IO)")
        }
    }


    private fun createErrorResponse(chain: Interceptor.Chain, message: String): Response {
        val errorBody = ResponseBody.create("application/json".toMediaType(), "{}")
        return Response.Builder()
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .code(504)
            .message(message)
            .body(errorBody)
            .build()
    }
}
