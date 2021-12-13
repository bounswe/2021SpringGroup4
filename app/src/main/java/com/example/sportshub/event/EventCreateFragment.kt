package com.example.sportshub.event

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sportshub.R
import java.util.*

class EventCreateFragment : Fragment() {
    private val args by navArgs<EventCreateFragmentArgs>()
    private lateinit var viewModel: EventCreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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
            hour = 0
            minute = 0
        }
        view.findViewById<TimePicker>(R.id.timePickerDuration).apply {
            setIs24HourView(true)
            hour = 0
            minute = 0
        }


        viewModel.eventCreateRequestModel.value!!.lat = args.latitude!!
        viewModel.eventCreateRequestModel.value!!.long = args.longitude!!

        view.findViewById<Button>(R.id.btnCreateEventLocation).setOnClickListener {
            findNavController().navigate(R.id.action_eventCreateFragment_to_createEventMapFragment)
        }

        return view
    }


}