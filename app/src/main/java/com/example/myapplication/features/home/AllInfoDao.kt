package com.example.myapplication.features.home

import androidx.room.*

@Dao
interface AllInfoDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertInfo(allInfoEntity: AllInfoEntity)

    @Query("select * from allInfo")
    suspend fun getInfo(): AllInfoEntity

}