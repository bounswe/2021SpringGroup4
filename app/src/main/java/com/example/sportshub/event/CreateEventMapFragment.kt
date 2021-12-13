package com.example.sportshub.event

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.sportshub.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CreateEventMapFragment : Fragment() {
    private var markerLocation = LatLng(41.0863, 29.0441)
    private lateinit var previousMarker: Marker
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        previousMarker = googleMap.addMarker(MarkerOptions().position(markerLocation).title("Marker in North Campus"))!!
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(markerLocation))
        googleMap.setMinZoomPreference(10.0F)

        googleMap.setOnMapClickListener {
            previousMarker.remove()
            markerLocation = it
            previousMarker = googleMap.addMarker(MarkerOptions().position(it).title("Event Location"))!!
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_event_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)



        view.findViewById<FloatingActionButton>(R.id.btnsaveMarkerLocation).setOnClickListener {
            val action : NavDirections = CreateEventMapFragmentDirections.
            actionCreateEventMapFragmentToEventCreateFragment().
            setLatitude(markerLocation.latitude.toString()).setLongitude(markerLocation.longitude.toString())
            findNavController().navigate(action)

        }


    }
}