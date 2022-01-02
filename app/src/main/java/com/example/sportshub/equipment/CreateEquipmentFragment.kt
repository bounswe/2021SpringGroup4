package com.example.sportshub.equipment

import android.app.Dialog
import android.icu.text.DecimalFormat
import android.location.Geocoder
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.sportshub.R
import com.example.sportshub.databinding.CreateEquipmentFragmentBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import java.io.IOException
import kotlin.concurrent.thread


class CreateEquipmentFragment : Fragment() {

    private lateinit var viewModel: CreateEquipmentViewModel
    private var _binding: CreateEquipmentFragmentBinding? = null

    private val binding get() = _binding!!
    var markerLocation = LatLng(41.0863, 29.0441)
    private lateinit var previousMarker: Marker
    private lateinit var th : Thread
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
            previousMarker = googleMap.addMarker(MarkerOptions().position(it).title("Equipment Location"))!!

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(CreateEquipmentViewModel::class.java)
        _binding = CreateEquipmentFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnConfirmCreateEquipment.setOnClickListener {
            viewModel.requestmodel.title = binding.editTextEquipmentTitle.text.toString()
            viewModel.requestmodel.description = binding.editTextEquipmentDescription.text.toString()
            viewModel.requestmodel.contact = binding.editTextContactNumber.text.toString()
            viewModel.requestmodel.sportType = binding.editTextSportType.text.toString()

            viewModel.createEquipment()
            th.join()
            findNavController().navigateUp()
        }


        var dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.fragment_create_equipment_map)
        dialog.window!!.setBackgroundDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.dialog_drawable_background
            )
        )
        dialog.window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        val geocoder = Geocoder(requireContext())

        binding.btnSetAddress.setOnClickListener {
            dialog.show()
            var map = dialog.findViewById<MapView>(R.id.equipment_map)
            map.onCreate(null)
            map.onStart()
            map.onResume()
            map.getMapAsync(callback)

            var btn = dialog.findViewById<FloatingActionButton>(R.id.btnsaveEquipmentLocation)
            btn.setOnClickListener {
                th = thread(start = true, priority = 1) {
                    try {
                        val df = DecimalFormat()
                        df.setMaximumFractionDigits(2)
                        val lat: Double = df.format(markerLocation.latitude).toDouble()
                        val lon: Double = df.format(markerLocation.longitude).toDouble()

                        viewModel.requestmodel.location = Geocoder(context).getFromLocation(lat, lon, 1)[0].getAddressLine(0)
                        Toast.makeText(requireContext(), viewModel.requestmodel.location, Toast.LENGTH_LONG)
                            .show()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }

                dialog.cancel()
            }

        }




        return root
    }

}