package com.example.myapplication.features.home.country

import com.example.myapplication.features.country.CountryEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryApi {

    @GET("countries/{country}")
    suspend fun getCountry(@Path("country") country: String): CountryEntity

}