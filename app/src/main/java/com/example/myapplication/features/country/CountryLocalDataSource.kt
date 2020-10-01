package com.example.myapplication.features.home.country

import androidx.room.withTransaction
import com.example.myapplication.core.AppDatabase
import com.example.myapplication.core.MyApp
import com.example.myapplication.features.country.CountryEntity
import com.example.myapplication.features.country.CountryInfoEntity

class CountryLocalDataSource {

    private val db = AppDatabase.buildDatabase(MyApp.app)

    suspend fun getCountry(country: String) = db.countryDao().getCountry(country)

    suspend fun getCountryInfo(country: String) = db.countryInfoDao().getCountryInfo(country)

    suspend fun insertCountry(data: CountryEntity?) {
        db.withTransaction {
            data?.let {
                db.countryDao().insertCountry(data)
                data.countryInfo?.let { infoEntity -> insertCountryInfo(data.country, infoEntity) }
            }
        }
    }

    private suspend fun insertCountryInfo(country: String, countyInfo: CountryInfoEntity) {
        countyInfo.countryName = country
        db.countryInfoDao().insertCountryInfo(countyInfo)
    }

}