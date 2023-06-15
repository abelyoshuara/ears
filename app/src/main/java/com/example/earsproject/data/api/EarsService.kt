package com.example.earsproject.data.api


import com.example.earsproject.data.remote.ears.GetAllHospitalsResponse


import retrofit2.http.*

interface EarsService {
    @GET("hospitals")
     suspend fun getAllHospitals(): GetAllHospitalsResponse

}