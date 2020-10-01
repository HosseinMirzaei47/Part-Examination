package com.example.myapplication.features.home.country

import androidx.room.withTransaction
import com.example.myapplication.core.AppDatabase
import com.example.myapplication.core.MyApp

class CountryLocalDataSource {

    private val db = AppDatabase.buildDatabase(MyApp.app)

    suspend fun getCountry(country: String) = db.countryDao().getCountry(country)

    suspend fun getCountryInfo(country: String) = db.countryInfoDao().getCountryInfo(country)

    suspend fun insertCountry(data: CountryEntity?) {
        db.withTransaction {
            data?.let {
                db.countryDao().insertCountry(data)
                insertCountryInfo(data.country, data.countryInfo)
            }
        }
    }

    private suspend fun insertCountryInfo(country: String, countyInfo: CountryInfoEntity) {
        countyInfo.countryName = country
        db.countryInfoDao().insertCountryInfo(countyInfo)
    }

}