package com.example.myapplication.features.home

import com.example.myapplication.core.MyApp
import com.example.myapplication.core.resource.Resource
import com.example.myapplication.core.utils.NetworkHelper
import com.example.myapplication.models.AllCountriesResponse
import com.example.nattramn.core.resource.Status

class HomeRepository(
    private val homeRemoteDataSource: HomeRemoteDataSource,
    private var homeLocalDataSource: HomeLocalDataSource
) {

    companion object {
        private var homeInstance: HomeRepository? = null

        fun getInstance(): HomeRepository {
            if (homeInstance == null) {
                synchronized(this) {
                    homeInstance =
                        HomeRepository(
                            HomeRemoteDataSource(),
                            HomeLocalDataSource()
                        )
                }
            }
            return homeInstance!!
        }
    }

    suspend fun getAllInfoDb() = homeLocalDataSource.getAllInfo()

    suspend fun getAllCountriesDb() = homeLocalDataSource.getAllCountries()

    suspend fun getAllInfo(): Resource<AllInfoEntity> {
        var response = Resource<AllInfoEntity>(Status.ERROR, null, null)

        if (NetworkHelper.isOnline(MyApp.app)) {

            val request = homeRemoteDataSource.getAllInfo()

            if (request.status == Status.SUCCESS) {

                response = Resource.success(request.data)
                homeLocalDataSource.insertAllInfo(request.data)

            } else if (request.status == Status.ERROR) {
                request.message?.let {
                    response = Resource.error(it, null)
                }
            }

        }

        return response

    }

    suspend fun getAllCountries(): Resource<AllCountriesResponse> {
        var response = Resource<AllCountriesResponse>(Status.ERROR, null, null)

        if (NetworkHelper.isOnline(MyApp.app)) {

            val request = homeRemoteDataSource.getAllCountries()

            if (request.status == Status.SUCCESS) {

                response = Resource.success(request.data)
                homeLocalDataSource.insertCountry(request.data)

            } else if (request.status == Status.ERROR) {
                request.message?.let {
                    response = Resource.error(it, null)
                }
            }

        }

        return response

    }

}