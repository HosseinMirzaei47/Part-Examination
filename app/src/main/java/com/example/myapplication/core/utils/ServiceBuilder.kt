package com.example.myapplication.core.utils

import androidx.lifecycle.MutableLiveData
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private val responseStatus = MutableLiveData<Response>()
    private var logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(Interceptor { chain ->
            val proceed = chain.proceed(request = chain.request())
            responseStatus.postValue(proceed)
            proceed
        })
        .addNetworkInterceptor(logging)
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl("https://corona.lmao.ninja/v2/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> buildService(service: Class<T>): T {
        return retrofit.create(service)
    }
}
