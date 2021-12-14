package com.example.sportshub.event

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.example.sportshub.R
import com.example.sportshub.SingletonRequestQueueProvider
import com.example.sportshub.databinding.EventDetailFragmentBinding

class EventDetailFragment : Fragment() {
    private lateinit var eventDetailViewModel: EventDetailViewModel
    private var _binding: EventDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<EventDetailFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        eventDetailViewModel = ViewModelProvider(this).get(EventDetailViewModel::class.java)
        _binding = EventDetailFragmentBinding.inflate(inflater, container, false)
        val root : View = binding.root

        var months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

        fun visibility(button: Button, visible: Boolean){
            button.isEnabled = visible
            button.isClickable = visible
            button.isVisible = visible
        }

        if(args.eventModel.creator.equals(SingletonRequestQueueProvider.getUsername())){
            visibility(binding.btnApplyEvent, false)
            visibility(binding.btnUndoApplyEvent, false)
            binding.eventDetailInfo1.text = "You created this event!"
            binding.eventDetailInfo2.isVisible = false
        } else if(args.eventModel.participants.contains(SingletonRequestQueueProvider.getUsername())){
            binding.btnApplyEvent.isEnabled = false
            binding.btnApplyEvent.isClickable = false
            visibility(binding.btnUndoApplyEvent, false)
            binding.eventDetailInfo2.text = "You are already a participant\nof this event!"
            binding.eventDetailInfo1.isVisible = false
        } else if(args.eventModel.applicants.contains(SingletonRequestQueueProvider.getUsername())){
            visibility(binding.btnApplyEvent, false)
            binding.eventDetailInfo1.isVisible = false
            binding.eventDetailInfo2.isVisible = false
        } else if(args.eventModel.participants.size >= args.eventModel.maxPlayers){
            binding.btnApplyEvent.isEnabled = false
            binding.btnApplyEvent.isClickable = false
            visibility(binding.btnUndoApplyEvent, false)
            binding.eventDetailInfo2.text = "No more spots available\nat this event!"
            binding.eventDetailInfo1.isVisible = false
        } else{
            visibility(binding.btnUndoApplyEvent, false)
            binding.eventDetailInfo1.isVisible = false
            binding.eventDetailInfo2.isVisible = false
        }

        eventDetailViewModel.event!!.value = args.eventModel
        eventDetailViewModel.event!!.observe(viewLifecycleOwner,{
            binding.eventDetailEventTitle.text = it.title
            if(it.description == ""){
                binding.eventDetailEventDescription.isVisible = false
            } else{
                binding.eventDetailEventDescription.text = it.description
            }
            binding.eventDetailEventCreator.text = "${it.creator} created this event"
            val dateParse = it.date.split("-")
            binding.eventDetailEventDate.text = "${dateParse[2]} ${months[dateParse[1].toInt()-1]} ${dateParse[0]}"
            val timeParse = it.time.split(":")
            binding.eventDetailEventTime.text = "${timeParse[0]}:${timeParse[1]}"
            val durationParse = it.duration.split(":")
            binding.eventDetailEventDuration.text = "${durationParse[0]}:${durationParse[1]}"
            binding.eventDetailEventLocation.text = it.location
            binding.eventDetailEventSportType.text = it.sportType
            binding.eventDetailEventRemainingSpots.text = "${(it.maxPlayers - it.participants.size).toString()} spots left"


        })

        binding.btnApplyEvent.setOnClickListener {
            eventDetailViewModel.applyEvent(requireContext(),
                object: ApplyEventListener() {
                    override fun onError() {
                        // error
                    }

                    override fun onResponse() {
                        Toast.makeText(requireContext(),"You applied for event \"${binding.eventDetailEventTitle.text}\"", Toast.LENGTH_SHORT).show()
                        visibility(binding.btnApplyEvent, false)
                        visibility(binding.btnUndoApplyEvent, true)
                    }
                })
        }

        binding.btnUndoApplyEvent.setOnClickListener {
            eventDetailViewModel.undoApplyEvent(requireContext(),
                object: ApplyEventListener() {
                    override fun onError() {
                        // error
                    }

                    override fun onResponse() {
                        Toast.makeText(requireContext(),"You are no longer applied for event \"${binding.eventDetailEventTitle.text}\"", Toast.LENGTH_SHORT).show()
                        visibility(binding.btnApplyEvent, true)
                        visibility(binding.btnUndoApplyEvent, false)
                    }
                })
        }


        return root
    }

}