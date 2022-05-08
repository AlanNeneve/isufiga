package com.example.iletsufigastore.webservice

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ProductsWebServiceFactory {

    private const val BASE_URL = "https://fakestoreapi.com/"

    fun createWebService(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideHttpClient())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }

    private fun provideHttpClient() = OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(30,TimeUnit.SECONDS)
        .build()
}