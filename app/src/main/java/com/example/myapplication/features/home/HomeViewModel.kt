package com.example.myapplication.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.resource.Resource
import com.example.myapplication.features.home.country.CountryEntity
import com.example.myapplication.models.AllCountriesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val homeRepository = HomeRepository.getInstance()

    private var _allInfoResult = MutableLiveData<Resource<AllInfoEntity>>()
    val allInfoResult: LiveData<Resource<AllInfoEntity>> get() = _allInfoResult

    private var _allCountriesResult = MutableLiveData<Resource<AllCountriesResponse>>()
    val allCountriesResult: LiveData<Resource<AllCountriesResponse>> get() = _allCountriesResult

    private var _allCountriesDbResult = MutableLiveData<List<CountryEntity>>()
    val allCountriesDbResult: LiveData<List<CountryEntity>> get() = _allCountriesDbResult

    private var _allInfoDbResult = MutableLiveData<AllInfoEntity>()
    val allInfoDbResult: LiveData<AllInfoEntity> get() = _allInfoDbResult

    fun getAllInfoDb() {
        viewModelScope.launch(Dispatchers.IO) {
            _allInfoDbResult.postValue(homeRepository.getAllInfoDb())
        }
    }

    fun getAllCountriesDb() {
        viewModelScope.launch(Dispatchers.IO) {
            _allCountriesDbResult.postValue(homeRepository.getAllCountriesDb())
        }
    }

    fun getAllInfo() {
        _allInfoResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _allInfoResult.postValue(homeRepository.getAllInfo())
        }
    }

    fun getAllCountries() {
        _allCountriesResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _allCountriesResult.postValue(homeRepository.getAllCountries())
        }
    }

}