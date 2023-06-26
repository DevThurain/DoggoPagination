package com.development.doggopagination.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class TheDoggoClient {
    var mTheDoggoApi: TheDoggoApi

    init {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofitClient = Retrofit.Builder()
            .baseUrl(ApiConstants.BASED_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mTheDoggoApi = retrofitClient.create(TheDoggoApi::class.java)

    }
}