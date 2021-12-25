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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
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

        if(args.eventModel.creator == SingletonRequestQueueProvider.getUsername()){
            visibility(binding.btnApplyEvent, false)
            visibility(binding.btnUndoApplyEvent, false)
            binding.eventDetailEventCreator.text = "You created this event"
            binding.eventDetailInfo.isVisible = false
        } else if(args.eventModel.participants.contains(SingletonRequestQueueProvider.getUsername())){
            binding.btnApplyEvent.isEnabled = false
            binding.btnApplyEvent.isClickable = false
            visibility(binding.btnUndoApplyEvent, false)
            binding.eventDetailInfo.text = "You are already a participant\nof this event!"
            visibility(binding.btnDeleteEvent, false)
        } else if(args.eventModel.applicants.contains(SingletonRequestQueueProvider.getUsername())){
            visibility(binding.btnApplyEvent, false)
            binding.eventDetailInfo.isVisible = false
            visibility(binding.btnDeleteEvent, false)
        } else if(args.eventModel.participants.size >= args.eventModel.maxPlayers){
            binding.btnApplyEvent.isEnabled = false
            binding.btnApplyEvent.isClickable = false
            visibility(binding.btnUndoApplyEvent, false)
            binding.eventDetailInfo.text = "No more spots available\nat this event!"
            visibility(binding.btnDeleteEvent, false)
        } else{
            visibility(binding.btnUndoApplyEvent, false)
            binding.eventDetailInfo.isVisible = false
            visibility(binding.btnDeleteEvent, false)
        }

        if(args.eventModel.comments.size == 0){
            binding.eventDetailEventComment.text = "No comments available!"
        }

        eventDetailViewModel.event!!.value = args.eventModel
        eventDetailViewModel.event!!.observe(viewLifecycleOwner,{
            binding.eventDetailEventTitle.text = it.title
            if(it.description == ""){
                binding.eventDetailEventDescription.isVisible = false
            } else{
                binding.eventDetailEventDescription.text = it.description
            }
            if(it.creator != SingletonRequestQueueProvider.getUsername()){
                binding.eventDetailEventCreator.text = "${it.creator} created this event"
            }
            val dateParse = it.date.split("-")
            binding.eventDetailEventDate.text = "${dateParse[2]} ${months[dateParse[1].toInt()-1]} ${dateParse[0]}"
            val timeParse = it.time.split(":")
            binding.eventDetailEventTime.text = "${timeParse[0]}:${timeParse[1]}"
            val durationParse = it.duration.split(":")
            binding.eventDetailEventDuration.text = "${durationParse[0]}:${durationParse[1]}"
            binding.eventDetailEventLocation.text = it.location
            binding.eventDetailEventSportType.text = it.sportType
            binding.eventDetailEventRemainingSpots.text = "${(it.maxPlayers - it.participants.size).toString()} spots left"
            val rw: RecyclerView = binding.listComment
            val adapter = CommentAdapter()
            rw.adapter = adapter
            adapter.commentList = it.comments
            adapter.notifyDataSetChanged()

        })

        binding.btnAddComment.setOnClickListener {
            eventDetailViewModel.addComment(requireContext(), binding.editTextAddComment.text.toString(),
                object: AddCommentListener() {
                    override fun onError() {
                        // error
                    }

                    override fun onResponse() {
                        Toast.makeText(requireContext(),"Your comment has been successfully added!", Toast.LENGTH_SHORT).show()
                        binding.editTextAddComment.text.clear()
                    }
                })
        }

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

        binding.btnDeleteEvent.setOnClickListener {
            eventDetailViewModel.deleteEvent(requireContext(),
                object: DeleteEventListener() {
                    override fun onError() {
                        // error
                    }

                    override fun onResponse() {
                        Toast.makeText(requireContext(),"Your event is successfully deleted!", Toast.LENGTH_SHORT).show()
                        findNavController().navigateUp()
                    }
                })
        }   


        return root
    }

}