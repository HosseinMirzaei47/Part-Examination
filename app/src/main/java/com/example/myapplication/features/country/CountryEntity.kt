package com.example.myapplication.features.country

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "country")
data class CountryEntity(
    @PrimaryKey var country: String,
    var active: Int,
    var activePerOneMillion: Double,
    var cases: Int,
    var casesPerOneMillion: Int,
    var continent: String,
    var critical: Int,
    var criticalPerOneMillion: Double,
    var deaths: Int,
    var deathsPerOneMillion: Int,
    var oneCasePerPeople: Int,
    var oneDeathPerPeople: Int,
    var oneTestPerPeople: Int,
    var population: Int,
    var recovered: Int,
    var recoveredPerOneMillion: Double,
    var tests: Int,
    var testsPerOneMillion: Int,
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
        0,
        0,
        "",
        0,
        0.0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0.0,
        0,
        0,
        0,
        0,
        0,
        0,
        null
    )

}