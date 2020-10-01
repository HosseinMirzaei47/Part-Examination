package com.example.myapplication.features.home

import androidx.room.withTransaction
import com.example.myapplication.core.AppDatabase
import com.example.myapplication.core.MyApp
import com.example.myapplication.features.country.CountryEntity
import com.example.myapplication.features.country.CountryInfoEntity

class HomeLocalDataSource {

    private val db = AppDatabase.buildDatabase(MyApp.app)

    suspend fun insertAllInfo(data: AllInfoEntity?) {
        data?.let {
            db.allInfoDao().insertInfo(it)
        }
    }

    suspend fun insertCountry(data: List<CountryEntity>?) {
        db.withTransaction {
            data?.let {
                db.countryDao().insertCountry(data)
                data.forEach { country ->
                    country.countryInfo?.let { infoEntity ->
                        insertCountryInfo(
                            country.country,
                            infoEntity
                        )
                    }
                }
            }
        }
    }

    private suspend fun insertCountryInfo(country: String, countyInfo: CountryInfoEntity) {
        countyInfo.countryName = country
        db.countryInfoDao().insertCountryInfo(countyInfo)
    }

    suspend fun getAllCountries() = db.countryDao().getAllCountries()

    suspend fun getAllInfo() = db.allInfoDao().getInfo()

}