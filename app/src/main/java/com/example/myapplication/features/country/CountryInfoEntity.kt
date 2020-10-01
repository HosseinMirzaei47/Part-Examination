package com.example.myapplication.features.home.country

import androidx.room.Entity

@Entity(tableName = "countryInfo")
data class CountryInfoEntity(
    val _id: Int,
    val flag: String?,
    val iso2: String?,
    val iso3: String?,
    val lat: Int,
    val long: Int,
    var countryName: String
)