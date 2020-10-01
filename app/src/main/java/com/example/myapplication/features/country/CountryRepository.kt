package com.example.myapplication.features.home.country

import com.example.myapplication.core.MyApp
import com.example.myapplication.core.resource.Resource
import com.example.myapplication.core.utils.NetworkHelper
import com.example.myapplication.features.country.CountryEntity
import com.example.nattramn.core.resource.Status

class CountryRepository(
    private val countryLocalDataSource: CountryLocalDataSource,
    private val countryRemoteDataSource: CountryRemoteDataSource
) {

    companion object {
        private var homeInstance: CountryRepository? = null

        fun getInstance(): CountryRepository {
            if (homeInstance == null) {
                synchronized(this) {
                    homeInstance =
                        CountryRepository(
                            CountryLocalDataSource(),
                            CountryRemoteDataSource()

                        )
                }
            }
            return homeInstance!!
        }
    }

    suspend fun getCountryDb(countryName: String) =
        countryLocalDataSource.getCountry(countryName)

    suspend fun getCountryInfoDb(countryName: String) =
        countryLocalDataSource.getCountryInfo(countryName)

    suspend fun getCountry(countryName: String): Resource<CountryEntity> {
        var response = Resource<CountryEntity>(Status.ERROR, null, null)

        if (NetworkHelper.isOnline(MyApp.app)) {
            val request = countryRemoteDataSource.getCountry(countryName)

            if (request.status == Status.SUCCESS) {

                response = Resource.success(request.data)
                countryLocalDataSource.insertCountry(request.data)

            } else if (request.status == Status.ERROR) {
                request.message?.let {
                    response = Resource.error(it, null)
                }
            }

        }

        return response
    }

}