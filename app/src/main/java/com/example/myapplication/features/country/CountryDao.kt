package com.example.myapplication.features.home.country

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

interface CountryDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCountry(countryEntity: List<CountryEntity>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCountry(countryEntity: CountryEntity)

    @Query("select * from country where country =:country")
    suspend fun getCountry(country: String): CountryEntity

    @Query("select * from country")
    suspend fun getAllCountries(): List<CountryEntity>

}