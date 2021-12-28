package com.example.sportshub.event

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sportshub.R
import com.example.sportshub.event.model.EventModel

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
    private val args by navArgs<CreateEventMapFragmentArgs>()
    private lateinit var sharedViewModel: SharedViewModel
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

        if(args.createOrUpdate == "c"){
            previousMarker = googleMap.addMarker(MarkerOptions().position(markerLocation).title("Marker in North Campus"))!!
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(markerLocation))
            googleMap.setMinZoomPreference(10.0F)

            googleMap.setOnMapClickListener {
                previousMarker.remove()
                markerLocation = it
                previousMarker = googleMap.addMarker(MarkerOptions().position(it).title("Event Location"))!!

            }
        } else if(args.createOrUpdate == "u"){
            markerLocation = LatLng(sharedViewModel.lat, sharedViewModel.long)
            previousMarker = googleMap.addMarker(MarkerOptions().position(markerLocation).title("Previous Location of Event"))!!
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(markerLocation))
            googleMap.setMinZoomPreference(10.0F)

            googleMap.setOnMapClickListener {
                previousMarker.remove()
                markerLocation = it
                previousMarker = googleMap.addMarker(MarkerOptions().position(it).title("Event Location"))!!

            }
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

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        view.findViewById<FloatingActionButton>(R.id.btnsaveMarkerLocation).setOnClickListener {
            if(args.createOrUpdate == "c"){
                val action : NavDirections = CreateEventMapFragmentDirections.
                actionCreateEventMapFragmentToEventCreateFragment().
                setLatitude(markerLocation.latitude.toFloat()).setLongitude(markerLocation.longitude.toFloat())
                findNavController().navigate(action)
            }
            else if(args.createOrUpdate == "u"){
                sharedViewModel.lat = markerLocation.latitude
                sharedViewModel.long = markerLocation.longitude
                findNavController().navigateUp()
            }
        }


    }
}