package com.example.feesight_mobile.data.retrofit

import android.content.Context
import com.example.feesight_mobile.BuildConfig.API_KEY
import com.example.feesight_mobile.BuildConfig.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {

    private lateinit var retrofit: Retrofit

    fun initialize(context: Context) {
        val authInterceptor = Interceptor { chain ->
            val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val token = sharedPreferences.getString("token", null)
            val req = chain.request()
            val requestHeaders = req.newBuilder()

            if (token != null) {
                requestHeaders.addHeader("Authorization", token)
            }

            requestHeaders.addHeader("API-Key", API_KEY)
            chain.proceed(requestHeaders.build())
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS) // mengatur timeout connect
            .readTimeout(30, TimeUnit.SECONDS)    // mengatur timeout baca
            .writeTimeout(30, TimeUnit.SECONDS)   // mengatur timeout tulis
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun getApiService(): ApiService {
        if (!this::retrofit.isInitialized) {
            throw IllegalStateException("ApiConfig is not initialized. Call initialize() before using this method.")
        }
        return retrofit.create(ApiService::class.java)
    }
}
