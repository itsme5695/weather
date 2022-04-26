package com.example.weather

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.weather.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private val TAG = "MapsActivity"
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var latling2: LatLng
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getDeviceLocation()
    }
    var marker:MarkerOptions? = null
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapClickListener {
            mMap.clear()
            marker = MarkerOptions().position(it)
            mMap.addMarker(marker!!)
           latling2 = it
            Log.d(TAG, "onMapReady: $latling2")
            val current = CameraUpdateFactory.newLatLngZoom(it, 15f)
            mMap.animateCamera(current)
            binding.fab.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("lat", latling2.latitude)
                intent.putExtra("long", latling2.longitude)

                startActivity(intent)
            }
        }
    }
    private fun getDeviceLocation() {
        try {
            askPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION) {
                //all permissions already granted or just granted
                fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this,
                        OnSuccessListener<Location?> { location ->
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                mMap?.moveCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        LatLng(
                                            location.latitude,
                                             location.longitude
                                        ), 17.0f
                                    )
                                )
                            }
                        })
                    .addOnFailureListener{
                    }
            }.onDeclined { e ->
                if (e.hasDenied()) {

                    AlertDialog.Builder(this)
                        .setMessage("Please accept our permissions")
                        .setPositiveButton("yes") { dialog, which ->
                            e.askAgain();
                        } //ask again
                        .setNegativeButton("no") { dialog, which ->
                            dialog.dismiss();
                        }
                        .show();
                }
                if (e.hasForeverDenied()) {
                    //the list of forever denied permissions, user has check 'never ask again'
                    // you need to open setting manually if you really need it
                    e.goToSettings();
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }
}