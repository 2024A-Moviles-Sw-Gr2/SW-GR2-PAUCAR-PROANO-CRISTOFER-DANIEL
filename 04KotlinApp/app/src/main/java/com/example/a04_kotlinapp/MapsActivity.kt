package com.example.a04_kotlinapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Marker

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtener el MapFragment y notificar cuando esté listo
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Obtener todas las empresas y mostrarlas en el mapa
        val allCompanies = DataBase.tables!!.getAllCompanies()

        for (company in allCompanies) {
            val location = LatLng(company.latitude, company.longitude)
            val marker = mMap.addMarker(
                MarkerOptions()
                    .position(location)
                    .title(company.name)
            )
        }

        if (allCompanies.isNotEmpty()) {
            val firstCompanyLocation = LatLng(allCompanies[0].latitude, allCompanies[0].longitude)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstCompanyLocation, 10f))
        }
    }
}
