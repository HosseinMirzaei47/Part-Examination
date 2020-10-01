package com.example.myapplication.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.features.home.AllInfoDao
import com.example.myapplication.features.home.AllInfoEntity
import com.example.myapplication.features.home.country.CountryDao
import com.example.myapplication.features.home.country.CountryInfoDao
import com.example.myapplication.features.home.country.CountryInfoEntity
import com.example.myapplication.models.CountryInfo

@Database(
    entities = [AllInfoEntity::class, CountryInfo::class, CountryInfoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun allInfoDao(): AllInfoDao
    abstract fun countryDao(): CountryDao
    abstract fun countryInfoDao(): CountryInfoDao

    companion object {

        private const val databaseName = "jalil-db"

        fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context, AppDatabase::class.java,
                databaseName
            )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }

    }

}

