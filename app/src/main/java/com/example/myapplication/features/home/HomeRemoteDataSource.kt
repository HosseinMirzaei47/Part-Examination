package com.example.myapplication.features.home

import com.example.myapplication.core.utils.ServiceBuilder
import com.example.myapplication.core.utils.safeApiCall

class HomeRemoteDataSource {

    suspend fun getAllInfo() = safeApiCall {
        ServiceBuilder.buildService(HomeApi::class.java).getAllInfo()
    }

    suspend fun getAllCountries() = safeApiCall {
        ServiceBuilder.buildService(HomeApi::class.java).getAllCountries()
    }

}