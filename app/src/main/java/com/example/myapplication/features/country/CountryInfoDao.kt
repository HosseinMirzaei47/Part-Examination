package com.example.myapplication.features.home.country

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

interface CountryInfoDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCountryInfo(countryInfoEntity: CountryInfoEntity)

    @Query("select * from countryInfo where countryName =:country")
    suspend fun getCountryInfo(country: String): CountryInfoEntity

}