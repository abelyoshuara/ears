package com.example.earsproject.data.di


import com.example.earsproject.data.api.EarsConfig
import com.example.earsproject.data.repository.EarsRepository

object Injection {
    fun provideEarsRepository(): EarsRepository {
        val apiService = EarsConfig.getApiService()
        return EarsRepository.getInstance(apiService)
    }
}