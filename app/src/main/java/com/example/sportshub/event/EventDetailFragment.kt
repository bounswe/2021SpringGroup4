package com.example.sportshub.event

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sportshub.databinding.EventDetailFragmentBinding

class EventDetailFragment : Fragment() {
    private lateinit var eventDetailViewModel: EventDetailViewModel
    private var _binding: EventDetailFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        eventDetailViewModel = ViewModelProvider(this).get(EventDetailViewModel::class.java)
        _binding = EventDetailFragmentBinding.inflate(inflater, container, false)
        val root : View = binding.root
        eventDetailViewModel.event!!.value = savedInstanceState!!.getParcelable("eventModel")
        eventDetailViewModel.event!!.observe(viewLifecycleOwner,{
            binding.eventDetailEventTitle.text = it.title
            binding.eventDetailEventDescription.text = it.description
            binding.eventDetailEventCreator.text = it.creator
            binding.eventDetailEventDate.text = it.date
            binding.eventDetailEventTime.text = it.time
            binding.eventDetailEventDuration.text = it.duration
            binding.eventDetailEventLocation.text = it.location
            binding.eventDetailEventSportType.text = it.sportType
            binding.eventDetailEventRemainingSpots.text = (it.maxPlayers - it.participants.size).toString()


        })


        return root
    }



}