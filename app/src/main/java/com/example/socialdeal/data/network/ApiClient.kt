package com.example.socialdeal.data.network

import com.example.socialdeal.data.Constants
import com.example.socialdeal.data.network.services.DealsService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient private constructor() {

    val dealsService: DealsService by lazy {
        retrofit.create(DealsService::class.java)
    }

    private val retrofit = createRetrofit()

    private fun createRetrofit(): Retrofit {
        val gsonBuilder = GsonBuilder().create()

        val okHttpClientBuilder = OkHttpClient.Builder()

        if (Constants.DEBUG) {
            okHttpClientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .client(okHttpClientBuilder.build())
            .build()
    }

    companion object {

        @Volatile
        private var instance: ApiClient? = null

        fun getInstance(): ApiClient {
            return instance ?: synchronized(this) {
                instance ?: ApiClient().also { instance = it }
            }
        }
    }
}