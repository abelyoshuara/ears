package com.example.earsproject.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.earsproject.data.Result
import com.example.earsproject.data.remote.ears.GetAllHospitalsResponse
import com.example.earsproject.data.repository.EarsRepository
import kotlinx.coroutines.launch

class EarsViewModel(private val earsRepository: EarsRepository) : ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    var isLoading: LiveData<Boolean> = _isLoading

    private var _hospitals = MutableLiveData<GetAllHospitalsResponse?>()
    var hospitals: LiveData<GetAllHospitalsResponse?> = _hospitals

    fun getAllHospitals() {
        viewModelScope.launch {
            earsRepository.getAllHospitals().collect{
                when(it){
                    is Result.Loading -> {
                        _isLoading.value = true
                    }
                    is Result.Success -> {
                        _isLoading.value = false
                        _hospitals.value = it.data
                    }
                    is Result.Error -> {
                        _isLoading.value = false

                    }
                }
            }
        }
        return
    }
}