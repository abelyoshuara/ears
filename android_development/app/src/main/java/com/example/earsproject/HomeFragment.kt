package com.example.earsproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint


class HomeFragment : Fragment(){
    private val hospitals: ArrayList<Hospital> = ArrayList()
    val adapter = HomeAdapter(hospitals)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up button click listener


        fetchDataFromFirestore()
    }

    private fun fetchDataFromFirestore() {
        val db = FirebaseFirestore.getInstance()
        val hospitalsCollection = db.collection("hospitals")

        hospitalsCollection.get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val name = document.getString("name") ?: ""
                    val imageUrl = document.getString("image_url") ?: ""
                    val specialty = document.getString("specialty") ?: ""
                    val insurance = document.getString("insurance") ?: ""
                    val address = document.getString("address") ?: ""
                    val coordinate = document.get("coordinate")

                    if (coordinate is GeoPoint) {
                        val hospital = Hospital(name, imageUrl, specialty, insurance, address, coordinate)
                        hospitals.add(hospital)
                    }
                }

                //setupRecyclerView()
            }
            .addOnFailureListener { exception ->
                // Handle error
            }
    }

    private fun filterHospitals(query: String, filter: String) {
        // Filter the hospitals list based on the search query and filter option
        val filteredList = hospitals.filter { hospital ->
            when (filter) {
                "Nama" -> hospital.name.contains(query, true)
                "Spesialis" -> hospital.specialty.contains(query, true)
                "Asuransi" -> hospital.insurance.contains(query, true)
                else -> false
            }
        }
        updateData(filteredList)
    }

    private fun updateData(filteredList: List<Hospital>) {
        hospitals.clear()
        for (hospital in filteredList) {
            hospitals.add(hospital)
        }
    }

//    private fun setupRecyclerView() {
//
//        R.id.homeView->layoutManager = LinearLayoutManager(requireContext())
//        homeView.adapter = adapter
//        adapter.notifyDataSetChanged()
//    }
}