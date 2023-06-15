package com.example.earsproject

import com.google.firebase.firestore.GeoPoint

data class Hospital(
    val name: String,
    val image_url: String,
    val specialty: String,
    val insurance: String,
    val address: String,
    val coordinate: GeoPoint?


)


