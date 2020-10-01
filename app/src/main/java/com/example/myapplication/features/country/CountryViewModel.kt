package com.example.myapplication.features.home.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.resource.Resource
import com.example.myapplication.features.country.CountryEntity
import com.example.myapplication.features.country.CountryInfoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountryViewModel : ViewModel() {

    private val countryRepository = CountryRepository.getInstance()

    private var _countryResult = MutableLiveData<Resource<CountryEntity>>()
    val countryResult: LiveData<Resource<CountryEntity>> get() = _countryResult

    private var _countryDbResult = MutableLiveData<CountryEntity>()
    val countryDbResult: LiveData<CountryEntity> get() = _countryDbResult

    private var _countryInfoDbResult = MutableLiveData<CountryInfoEntity>()
    val countryInfoDbResult: LiveData<CountryInfoEntity> get() = _countryInfoDbResult

    fun getCountryDb(countryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _countryDbResult.postValue(countryRepository.getCountryDb(countryName))
        }
    }

    fun getCountryInfoDb(countryName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _countryInfoDbResult.postValue(countryRepository.getCountryInfoDb(countryName))
        }
    }

    fun getCountry(countryName: String) {

        _countryResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _countryResult.postValue(countryRepository.getCountry(countryName))
        }
    }

}