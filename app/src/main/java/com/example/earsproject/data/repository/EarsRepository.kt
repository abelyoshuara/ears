package com.example.earsproject.data.repository
import com.example.earsproject.data.Result
import com.example.earsproject.data.api.EarsService
import com.example.earsproject.data.remote.ears.GetAllHospitalsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EarsRepository private constructor(
   private val apiService: EarsService
) {
    fun getAllHospitals(): Flow<Result<GetAllHospitalsResponse>> {
        return flow {
            try {
                emit(Result.Loading)
             //val response = apiService.getAllHospitals()
                emit(Result.Error("Data Kosong"))
            } catch (ex: Exception) {
                emit(Result.Error(ex.message.toString()))
            }
        }
    }
    companion object {
        @Volatile
        private var instance: EarsRepository? = null
        fun getInstance(
            apiService: EarsService
        ): EarsRepository =
            instance ?: synchronized(this) {
                instance ?: EarsRepository(apiService)
            }.also { instance = it }
    }
}

