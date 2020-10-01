package com.example.myapplication.features.country

import androidx.room.*

@Dao
interface CountryInfoDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCountryInfo(countryInfoEntity: CountryInfoEntity)

    @Query("select * from countryInfo where countryName =:country")
    suspend fun getCountryInfo(country: String): CountryInfoEntity

}