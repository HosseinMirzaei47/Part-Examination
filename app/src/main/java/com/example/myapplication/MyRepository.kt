package com.example.myapplication

class MyRepository(
    private val remoteDataSource: RemoteDataSource,
    private var localDataSource: LocalDataSource
) {

    companion object {
        private var myInstance: MyRepository? = null

        fun getInstance(): MyRepository {
            if (myInstance == null) {
                synchronized(this) {
                    myInstance =
                        MyRepository(
                            RemoteDataSource(),
                            LocalDataSource()
                        )
                }
            }
            return myInstance!!
        }
    }
}