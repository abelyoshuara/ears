package com.example.earsproject.data.uii

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import com.example.earsproject.ui.viewmodel.EarsViewModel
import com.example.earsproject.ui.viewmodel.factory.EarsViewModelFactory
import com.example.earsproject.R
import com.example.earsproject.data.remote.ears.DocumentsItem
import com.example.earsproject.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var  fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101
    private lateinit var mMap: GoogleMap
  //  private var token = "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"
    private val earsViewModel: EarsViewModel by viewModels{
        EarsViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
    private var listStory = listOf<DocumentsItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.Map) as SupportMapFragment
        mapFragment.getMapAsync(this)

//        getToken()
        getDataStory()
        setupView()
//        setBottomNav()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fetchLocation()

        binding.callAmbulance.setOnClickListener {
            fetchLocation()
        }
    }


    private fun fetchLocation() {
        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }
        task.addOnSuccessListener {
            if (it != null) {
                Toast.makeText(
                    applicationContext,
                    "${it.latitude} ${it.longitude}",
                    Toast.LENGTH_SHORT
                ).show()
                val mapFragment = supportFragmentManager
                    .findFragmentById(R.id.Map) as SupportMapFragment
                mapFragment.getMapAsync(this)
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            permissionCode -> if(grantResults.isNotEmpty()&& grantResults[0]==
                    PackageManager.PERMISSION_GRANTED){
                fetchLocation()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        with(mMap){
            uiSettings.isIndoorLevelPickerEnabled = true
            uiSettings.isCompassEnabled = true
            uiSettings.isMapToolbarEnabled = true
        }
        getMyLocation()

        val Surabaya = LatLng(-7.250445,112.768845)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Surabaya, 15f))
    }
    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    //    private fun getToken(){
//        getStoryLocationViewModel.getUser().observe(this){
//            token="Bearer ${it.token}"
//        }
//    }
    private fun getDataStory(){
        earsViewModel.getAllHospitals()
        earsViewModel.hospitals.observe(this){
            listStory = it!!.documents
            setMapMarker()
        }
        earsViewModel.isLoading.observe(this){
            binding.progressBar.isVisible = it
        }
    }
    private fun setMapMarker(){
        listStory.forEachIndexed { index, it ->

            val latLng = LatLng(it.fields.coordinate.mapValue.fields.latitude.doubleValue, it.fields.coordinate.mapValue.fields.longitude.doubleValue)
            if(index==0){
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13f))
            }
            mMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(it.fields.name.stringValue)
                    .snippet(it.fields.roomCapacity.integerValue)
//                    .icon(vectorToBitmap(R.drawable.ic_location_marker, Color.parseColor("#E2659A")))
            )
            mMap.setOnMarkerClickListener { marker ->
                marker.showInfoWindow()
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 18f))
                return@setOnMarkerClickListener true
            }
        }
    }

    private fun vectorToBitmap(@DrawableRes id: Int, @ColorInt color: Int): BitmapDescriptor {
        val vectorDrawable = ResourcesCompat.getDrawable(resources, id, null)
        if (vectorDrawable == null) {
            Log.e("BitmapHelper", "Resource not found")
            return BitmapDescriptorFactory.defaultMarker()
        }
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        DrawableCompat.setTint(vectorDrawable, color)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    getMyLocation()
                }
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    getMyLocation()
                }
                else -> {}
            }
        }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
    private fun getMyLocation() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

//    private fun setMapStyle() {
//        try {
//            val success =mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
//            if (!success) {
//                Log.e(TAG, "Style parsing failed.")
//            }
//        } catch (exception: Resources.NotFoundException) {
//            Log.e(TAG, "Can't find style. Error: ", exception)
//        }
//    }
//    private fun setBottomNav(){
//        with(binding.bottomNavigation){
//            selectedItemId = R.id.location
//
//            setOnItemSelectedListener {
//                when(it.itemId){
//                    R.id.story -> {
//                        startActivity(Intent(this@StoryLocationActivity, MainActivity::class.java))
//                        overridePendingTransition(0, 0)
//                        return@setOnItemSelectedListener true
//                    }
//                    R.id.location -> return@setOnItemSelectedListener true
//                    R.id.add_story -> {
//                        startActivity(Intent(this@StoryLocationActivity, AddStoryActivity::class.java))
//                        overridePendingTransition(0, 0)
//                        return@setOnItemSelectedListener true
//                    }
//                    R.id.account ->  {
//                        startActivity(Intent(this@StoryLocationActivity, ProfileActivity::class.java))
//                        overridePendingTransition(0, 0)
//                        return@setOnItemSelectedListener true
//                    }
//                    else -> false
//                }
//            }
//        }
//    }

    companion object {
        private const val TAG = "MapsActivity"
    }
}