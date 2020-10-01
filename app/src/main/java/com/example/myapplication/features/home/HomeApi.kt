package com.example.myapplication.features.home

import com.example.myapplication.models.AllCountriesResponse
import retrofit2.http.GET

interface HomeApi {

    @GET("all")
    suspend fun getAllInfo(): AllInfoEntity

    @GET("countries")
    suspend fun getAllCountries(): AllCountriesResponse

}