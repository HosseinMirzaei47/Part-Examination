package com.example.myapplication.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.resource.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {

    private val homeRepository = HomeRepository.getInstance()

    private var _allInfoResult = MutableLiveData<Resource<AllInfoEntity>>()
    val allInfoResult: LiveData<Resource<AllInfoEntity>> get() = _allInfoResult

    private var _allInfoDbResult = MutableLiveData<AllInfoEntity>()
    val allInfoDbResult: LiveData<AllInfoEntity> get() = _allInfoDbResult

    fun getAllInfoDb() {
        viewModelScope.launch(Dispatchers.IO) {
            _allInfoDbResult.postValue(homeRepository.getAllInfoDb())
        }
    }

    fun getAllInfo() {
        _allInfoResult.value = Resource.loading(null)

        viewModelScope.launch(Dispatchers.IO) {
            _allInfoResult.postValue(homeRepository.getAllInfo())
        }
    }

}