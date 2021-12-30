package com.example.sportshub.event

import android.location.Geocoder
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sportshub.R
import com.example.sportshub.event.model.EventModel
import java.util.*

class EventUpdateFragment : Fragment() {
    //private val args by navArgs<EventUpdateFragmentArgs>()
    private lateinit var viewModel: EventUpdateViewModel
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.event_update_fragment, container, false)
        viewModel = ViewModelProvider(this).get(EventUpdateViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val currentChanges = MutableLiveData<EventModel>()

        var warning: String = ""
        var num: Int = 0
        fun stringConstruction(number: Int, word: String){
            if(number != 0){
                warning = warning + ", $word"
            } else{
                warning = warning + "$word"
            }
        }

        fun timeConstruction(timePicker: TimePicker): String{
            val hour = timePicker.hour
            val minute = timePicker.minute
            var retval = ""
            if(hour in 0..9){
                retval = retval + "0$hour"
            } else{
                retval = retval + "$hour"
            }
            if(minute in 0..9){
                retval = retval + ":0$minute"
            } else{
                retval = retval + ":$minute"
            }
            return retval
        }

        currentChanges.value = sharedViewModel.event.value
        currentChanges.observe(viewLifecycleOwner, {
            view.findViewById<EditText>(R.id.editTextTitle2).setText(it.title)
            view.findViewById<EditText>(R.id.editTextDescription2).setText(it.description)
            view.findViewById<EditText>(R.id.editTextSportType2).setText(it.sportType)
            view.findViewById<EditText>(R.id.editTextSkillLevel2).setText(it.skillLevel)
            if(it.maxPlayers == null){
                view.findViewById<EditText>(R.id.editTextMaxPlayers2).setText("")
            } else{
                view.findViewById<EditText>(R.id.editTextMaxPlayers2).setText(it.maxPlayers.toString())
            }
            view.findViewById<DatePicker>(R.id.createDatePicker2).apply {
                val now = Calendar.getInstance()
                minDate = now.timeInMillis
                val dateParse = it.date.split("-")
                updateDate(dateParse[0].toInt(), dateParse[1].toInt()-1, dateParse[2].toInt())
            }
            view.findViewById<TimePicker>(R.id.createTimePicker2).apply {
                setIs24HourView(true)
                val timeParse = it.time.split(":")
                hour = timeParse[0].toInt()
                minute = timeParse[1].toInt()
            }
            view.findViewById<TimePicker>(R.id.timePickerDuration2).apply {
                setIs24HourView(true)
                val durationParse = it.duration.split(":")
                hour = durationParse[0].toInt()
                minute = durationParse[1].toInt()
            }
        })

        view.findViewById<Button>(R.id.btnCreateEventLocation2).setOnClickListener {
            currentChanges.value!!.title = view.findViewById<EditText>(R.id.editTextTitle2).text.toString()
            currentChanges.value!!.description = view.findViewById<EditText>(R.id.editTextDescription2).text.toString()
            currentChanges.value!!.sportType = view.findViewById<EditText>(R.id.editTextSportType2).text.toString()
            currentChanges.value!!.skillLevel = view.findViewById<EditText>(R.id.editTextSkillLevel2).text.toString()
            currentChanges.value!!.maxPlayers = view.findViewById<EditText>(R.id.editTextMaxPlayers2).text.toString().toIntOrNull()
            currentChanges.value!!.date = "${view.findViewById<DatePicker>(R.id.createDatePicker2).year}-${view.findViewById<DatePicker>(R.id.createDatePicker2).month+1}-${view.findViewById<DatePicker>(R.id.createDatePicker2).dayOfMonth}"
            currentChanges.value!!.time = "${view.findViewById<TimePicker>(R.id.createTimePicker2).hour}:${view.findViewById<TimePicker>(R.id.createTimePicker2).minute}"
            currentChanges.value!!.duration = "${view.findViewById<TimePicker>(R.id.timePickerDuration2).hour}:${view.findViewById<TimePicker>(R.id.timePickerDuration2).minute}"
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
            viewModel.eventUpdateRequestModel.value!!.time = timeConstruction(view.findViewById<TimePicker>(R.id.createTimePicker2))
            viewModel.eventUpdateRequestModel.value!!.duration = timeConstruction(view.findViewById<TimePicker>(R.id.timePickerDuration2))
            viewModel.eventUpdateRequestModel.value!!.lat = sharedViewModel.lat
            viewModel.eventUpdateRequestModel.value!!.long = sharedViewModel.long
            var location = Geocoder(activity).getFromLocation(sharedViewModel.lat, sharedViewModel.long,1)[0]
            viewModel.eventUpdateRequestModel.value!!.location = location.getAddressLine(0)
            if(viewModel.eventUpdateRequestModel.value!!.title.isNotBlank() &&
                viewModel.eventUpdateRequestModel.value!!.maxPlayers != null &&
                viewModel.eventUpdateRequestModel.value!!.maxPlayers != 0 &&
                viewModel.eventUpdateRequestModel.value!!.duration != "0:0"){
                viewModel.updateEvent(requireContext(), sharedViewModel.event.value!!.id,
                    object: UpdateEventListener() {
                        override fun onError() {
                            
                        }

                        override fun onResponse() {
                            sharedViewModel.event.value!!.title = viewModel.eventUpdateRequestModel.value!!.title
                            sharedViewModel.event.value!!.description = viewModel.eventUpdateRequestModel.value!!.description
                            sharedViewModel.event.value!!.sportType = viewModel.eventUpdateRequestModel.value!!.sportType
                            sharedViewModel.event.value!!.skillLevel = viewModel.eventUpdateRequestModel.value!!.skill_level
                            sharedViewModel.event.value!!.maxPlayers = viewModel.eventUpdateRequestModel.value!!.maxPlayers!!
                            sharedViewModel.event.value!!.date = viewModel.eventUpdateRequestModel.value!!.date
                            sharedViewModel.event.value!!.time = viewModel.eventUpdateRequestModel.value!!.time
                            sharedViewModel.event.value!!.duration = viewModel.eventUpdateRequestModel.value!!.duration
                            sharedViewModel.event.value!!.lat = viewModel.eventUpdateRequestModel.value!!.lat
                            sharedViewModel.event.value!!.long = viewModel.eventUpdateRequestModel.value!!.long
                            sharedViewModel.event.value!!.location = viewModel.eventUpdateRequestModel.value!!.location
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