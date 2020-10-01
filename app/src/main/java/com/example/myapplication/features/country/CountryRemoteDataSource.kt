package com.example.myapplication.features.home.country

import com.example.myapplication.core.utils.ServiceBuilder
import com.example.myapplication.core.utils.safeApiCall

class CountryRemoteDataSource {

    suspend fun getCountry(countryName: String) = safeApiCall {
        ServiceBuilder.buildService(CountryApi::class.java).getCountry(countryName)
    }

}