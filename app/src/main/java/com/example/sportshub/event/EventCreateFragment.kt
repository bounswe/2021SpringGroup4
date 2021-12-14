package com.example.sportshub.event

import android.content.Intent
import android.location.Geocoder
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sportshub.MainActivity
import com.example.sportshub.R
import com.example.sportshub.SingletonRequestQueueProvider
import com.example.sportshub.login.model.LoginResponseModel
import java.io.IOException
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class EventCreateFragment : Fragment() {
    private val args by navArgs<EventCreateFragmentArgs>()
    private lateinit var viewModel: EventCreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.event_create_fragment, container, false)
        viewModel = ViewModelProvider(this).get(EventCreateViewModel::class.java)

        view.findViewById<DatePicker>(R.id.createDatePicker).apply {
            val now = Calendar.getInstance()
            minDate = now.timeInMillis
            updateDate(now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
        }
        view.findViewById<TimePicker>(R.id.createTimePicker).apply {
            setIs24HourView(true)
            hour = hour
            minute = minute
        }
        view.findViewById<TimePicker>(R.id.timePickerDuration).apply {
            setIs24HourView(true)
            hour = 0
            minute = 0
        }

        viewModel.eventCreateRequestModel.value!!.lat = args.latitude.toDouble()
        viewModel.eventCreateRequestModel.value!!.long = args.longitude.toDouble()
        var location = Geocoder(activity).getFromLocation(args.latitude.toDouble(), args.longitude.toDouble(),1)[0]
        viewModel.eventCreateRequestModel.value!!.location = location.getAddressLine(0)

        view.findViewById<Button>(R.id.btnCreateEventLocation).setOnClickListener {
            findNavController().navigate(R.id.action_eventCreateFragment_to_createEventMapFragment)

        }

        view.findViewById<Button>(R.id.buttonCreateEvent).setOnClickListener {
            viewModel.eventCreateRequestModel.value!!.title = view.findViewById<EditText>(R.id.editTextTitle).text.toString()
            viewModel.eventCreateRequestModel.value!!.description = view.findViewById<EditText>(R.id.editTextDescription).text.toString()
            viewModel.eventCreateRequestModel.value!!.sportType = view.findViewById<EditText>(R.id.editTextSportType).text.toString()
            viewModel.eventCreateRequestModel.value!!.skill_level = view.findViewById<EditText>(R.id.editTextSkillLevel).text.toString()
            if(view.findViewById<EditText>(R.id.editTextMaxPlayers).text.toString() != ""){
                viewModel.eventCreateRequestModel.value!!.maxPlayers = view.findViewById<EditText>(R.id.editTextMaxPlayers).text.toString().toInt()
            }
            viewModel.eventCreateRequestModel.value!!.date = "${view.findViewById<DatePicker>(R.id.createDatePicker).year}-${view.findViewById<DatePicker>(R.id.createDatePicker).month+1}-${view.findViewById<DatePicker>(R.id.createDatePicker).dayOfMonth}"
            viewModel.eventCreateRequestModel.value!!.time = "${view.findViewById<TimePicker>(R.id.createTimePicker).hour}:${view.findViewById<TimePicker>(R.id.createTimePicker).minute}"
            viewModel.eventCreateRequestModel.value!!.duration = "${view.findViewById<TimePicker>(R.id.timePickerDuration).hour}:${view.findViewById<TimePicker>(R.id.timePickerDuration).minute}"
            viewModel.createEvent(requireContext(),
                object: CreateEventListener() {
                    override fun onError(warning: String) {
                        if(warning != "Event "){
                            Toast.makeText(context, warning, Toast.LENGTH_LONG).show()
                        }
                        viewModel.eventCreateRequestModel.value!!.maxPlayers = 0
                    }

                    override fun onResponse() {
                        Toast.makeText(context,"Your event is successfully created!", Toast.LENGTH_SHORT).show()
                        findNavController().navigateUp()
                    }
                })
        }

        return view
    }


}