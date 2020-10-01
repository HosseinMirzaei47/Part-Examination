package com.example.myapplication.models

import com.google.gson.annotations.SerializedName

data class CountryInfo(
    val _id: Int,
    val flag: String?,
    val iso2: String?,
    val iso3: String?,
    val lat: Double,
    @SerializedName("long")
    val longP: Double
)