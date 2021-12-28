package com.example.sportshub.event

import android.location.Geocoder
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sportshub.R
import java.util.*

class EventUpdateFragment : Fragment() {
    private val args by navArgs<EventUpdateFragmentArgs>()
    private lateinit var viewModel: EventUpdateViewModel
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.event_update_fragment, container, false)
        viewModel = ViewModelProvider(this).get(EventUpdateViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        var warning: String = ""
        var num: Int = 0
        fun stringConstruction(number: Int, word: String){
            if(number != 0){
                warning = warning + ", $word"
            } else{
                warning = warning + "$word"
            }
        }

        view.findViewById<EditText>(R.id.editTextTitle2).setText(sharedViewModel.event.value!!.title)
        view.findViewById<EditText>(R.id.editTextDescription2).setText(sharedViewModel.event.value!!.description)
        view.findViewById<EditText>(R.id.editTextSportType2).setText(sharedViewModel.event.value!!.sportType)
        view.findViewById<EditText>(R.id.editTextSkillLevel2).setText(sharedViewModel.event.value!!.skillLevel)
        view.findViewById<EditText>(R.id.editTextMaxPlayers2).setText(sharedViewModel.event.value!!.maxPlayers.toString())
        view.findViewById<DatePicker>(R.id.createDatePicker2).apply {
            val now = Calendar.getInstance()
            minDate = now.timeInMillis
            val dateParse = sharedViewModel.event.value!!.date.split("-")
            updateDate(dateParse[0].toInt(), dateParse[1].toInt()-1, dateParse[2].toInt())
        }
        view.findViewById<TimePicker>(R.id.createTimePicker2).apply {
            setIs24HourView(true)
            val timeParse = sharedViewModel.event.value!!.time.split(":")
            hour = timeParse[0].toInt()
            minute = timeParse[1].toInt()
        }
        view.findViewById<TimePicker>(R.id.timePickerDuration2).apply {
            setIs24HourView(true)
            val durationParse = sharedViewModel.event.value!!.duration.split(":")
            hour = durationParse[0].toInt()
            minute = durationParse[1].toInt()
        }


        viewModel.eventUpdateRequestModel.value!!.lat = args.latitude.toDouble()
        viewModel.eventUpdateRequestModel.value!!.long = args.longitude.toDouble()
        var location = Geocoder(activity).getFromLocation(args.latitude.toDouble(), args.longitude.toDouble(),1)[0]
        viewModel.eventUpdateRequestModel.value!!.location = location.getAddressLine(0)

        view.findViewById<Button>(R.id.btnCreateEventLocation2).setOnClickListener {
            val action: NavDirections = EventUpdateFragmentDirections.actionEventUpdateFragmentToCreateEventMapFragment().setCreateOrUpdate("u")
            findNavController().navigate(action)
        }

        view.findViewById<Button>(R.id.buttonUpdateEvent).setOnClickListener {
            viewModel.eventUpdateRequestModel.value!!.title = view.findViewById<EditText>(R.id.editTextTitle2).text.toString()
            viewModel.eventUpdateRequestModel.value!!.description = view.findViewById<EditText>(R.id.editTextDescription2).text.toString()
            viewModel.eventUpdateRequestModel.value!!.sportType = view.findViewById<EditText>(R.id.editTextSportType2).text.toString()
            viewModel.eventUpdateRequestModel.value!!.skill_level = view.findViewById<EditText>(R.id.editTextSkillLevel2).text.toString()
            viewModel.eventUpdateRequestModel.value!!.maxPlayers = view.findViewById<EditText>(R.id.editTextMaxPlayers2).text.toString().toIntOrNull()
            viewModel.eventUpdateRequestModel.value!!.date = "${view.findViewById<DatePicker>(R.id.createDatePicker2).year}-${view.findViewById<DatePicker>(R.id.createDatePicker2).month+1}-${view.findViewById<DatePicker>(R.id.createDatePicker2).dayOfMonth}"
            viewModel.eventUpdateRequestModel.value!!.time = "${view.findViewById<TimePicker>(R.id.createTimePicker2).hour}:${view.findViewById<TimePicker>(R.id.createTimePicker2).minute}"
            viewModel.eventUpdateRequestModel.value!!.duration = "${view.findViewById<TimePicker>(R.id.timePickerDuration2).hour}:${view.findViewById<TimePicker>(R.id.timePickerDuration2).minute}"
            if(viewModel.eventUpdateRequestModel.value!!.title.isNotBlank() &&
                viewModel.eventUpdateRequestModel.value!!.maxPlayers != null &&
                viewModel.eventUpdateRequestModel.value!!.maxPlayers != 0 &&
                viewModel.eventUpdateRequestModel.value!!.duration != "0:0"){
                viewModel.updateEvent(requireContext(), sharedViewModel.event.value!!.id,
                    object: UpdateEventListener() {
                        override fun onError() {
                            
                        }

                        override fun onResponse() {
                            Toast.makeText(context,"Your event is successfully updated!", Toast.LENGTH_SHORT).show()
                            findNavController().navigateUp()
                        }
                    })
            } else{
                warning = "Event "
                if(viewModel.eventUpdateRequestModel.value!!.title.isBlank()){
                    warning = warning + "title"
                    num = num + 1
                }
                if(viewModel.eventUpdateRequestModel.value!!.maxPlayers == null || viewModel.eventUpdateRequestModel.value!!.maxPlayers == 0){
                    stringConstruction(num, "player number")
                    num = num + 1
                }
                if(viewModel.eventUpdateRequestModel.value!!.duration == "0:0"){
                    stringConstruction(num, "duration")
                    num = num + 1
                }
                warning = warning + " required!"
                Toast.makeText(context, warning, Toast.LENGTH_SHORT).show()
                warning = ""
                num = 0
            }

        }

        return view
    }

}