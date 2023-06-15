package com.example.earsproject.ui.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.earsproject.data.di.Injection
import com.example.earsproject.data.repository.EarsRepository
import com.example.earsproject.ui.viewmodel.EarsViewModel

class EarsViewModelFactory private constructor(private val earsRepository: EarsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(EarsViewModel::class.java) -> {
                EarsViewModel(earsRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: EarsViewModelFactory? = null
        fun getInstance(context: Context): EarsViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: EarsViewModelFactory(Injection.provideEarsRepository())
            }.also { instance = it }
    }
}