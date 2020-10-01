package com.example.myapplication.features.country

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "country")
data class CountryEntity(
    @PrimaryKey var country: String,
    var active: Int,
    var activePerOneMillion: Double,
    var cases: Double,
    var casesPerOneMillion: Double,
    var continent: String,
    var critical: Int,
    var criticalPerOneMillion: Double,
    var deaths: Int,
    var deathsPerOneMillion: Double,
    var oneCasePerPeople: Double,
    var oneDeathPerPeople: Double,
    var oneTestPerPeople: Double,
    var population: Int,
    var recovered: Int,
    var recoveredPerOneMillion: Double,
    var tests: Int,
    var testsPerOneMillion: Double,
    var todayCases: Int,
    var todayDeaths: Int,
    var todayRecovered: Int,
    var updated: Long,
    @Ignore var countryInfo: CountryInfoEntity?
) {

    constructor() : this(
        "",
        0,
        0.0,
        0.0,
        0.0,
        "",
        0,
        0.0,
        0,
        0.0,
        0.0,
        0.0,
        0.0,
        0,
        0,
        0.0,
        0,
        0.0,
        0,
        0,
        0,
        0,
        null
    )

}