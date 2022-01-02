package com.example.sportshub.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportshub.SingletonRequestQueueProvider
import com.example.sportshub.databinding.FragmentProfileBinding
import com.example.sportshub.event.AddCommentListener
import com.example.sportshub.event.model.EventModel
import com.example.sportshub.profile.model.ProfileModel

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val profile = MutableLiveData<ProfileModel>()

        profileViewModel.getProfile(requireContext(), SingletonRequestQueueProvider.getUsername(),
            object: GetProfileListener() {
                override fun onError(statusCode: Int?) {
                    Toast.makeText(requireContext(),"Something wrong. Please try again!", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(profileModel: ProfileModel) {
                    profile.value = profileModel
                }
            })

        val rw_upcoming: RecyclerView = binding.listUpcomingEvents
        val adapter_upcoming = ProfileEventAdapter()
        rw_upcoming.adapter = adapter_upcoming
        rw_upcoming.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        val rw_applied: RecyclerView = binding.listAppliedEvents
        val adapter_applied = ProfileEventAdapter()
        rw_applied.adapter = adapter_applied
        rw_applied.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        val rw_badges: RecyclerView = binding.listBadges
        val adapter_badges = BadgeAdapter()
        rw_badges.adapter = adapter_badges
        rw_badges.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        profile.observe(viewLifecycleOwner, {
            binding.username.text = it.username
            if(it.first_name == ""){
                binding.firstName.isVisible = false
            } else{
                binding.firstName.isVisible = true
                binding.firstName.text = it.first_name
            }
            if(it.last_name == ""){
                binding.lastName.isVisible = false
            } else{
                binding.lastName.isVisible = true
                binding.lastName.text = it.last_name
            }
            if(it.age == 0){
                binding.age.isVisible = false
            } else{
                binding.age.isVisible = true
                binding.age.text = it.age.toString()
            }
            if(it.location == ""){
                binding.location.isVisible = false
            } else{
                binding.location.isVisible = true
                binding.location.text = it.location
            }
            if(it.about == ""){
                binding.about.isVisible = false
            } else{
                binding.about.isVisible = true
                binding.about.text = it.about
            }
            if(it.going.size == 0){
                binding.noUpcoming.isVisible = true
                binding.listUpcomingEvents.isVisible = false
                val params = binding.appliedEventsText.layoutParams as ConstraintLayout.LayoutParams
                params.topToBottom = binding.noUpcoming.id
                params.topMargin = 64
                binding.appliedEventsText.requestLayout()
            } else{
                binding.listUpcomingEvents.isVisible = true
                binding.noUpcoming.isVisible = false
                val params = binding.appliedEventsText.layoutParams as ConstraintLayout.LayoutParams
                params.topToBottom = binding.listUpcomingEvents.id
                params.topMargin = 0
                binding.appliedEventsText.requestLayout()
                var upcoming_event_list: MutableList<EventModel> = mutableListOf()
                for(i in 0 until it.going.size){
                    profileViewModel.getEventDetail(requireContext(), it.going.get(i),
                        object: GetEventDetailListener() {
                            override fun onError() {
                                // error
                            }

                            override fun onResponse(eventModel: EventModel) {
                                upcoming_event_list.add(eventModel)
                                if(upcoming_event_list.size == it.going.size){
                                    adapter_upcoming.eventList = upcoming_event_list
                                    adapter_upcoming.notifyDataSetChanged()
                                }
                            }
                        })
                }
            }
            if(it.applied.size == 0){
                binding.noApplied.isVisible = true
                binding.listAppliedEvents.isVisible = false
                val params = binding.badgesText.layoutParams as ConstraintLayout.LayoutParams
                params.topToBottom = binding.noApplied.id
                params.topMargin = 64
                binding.badgesText.requestLayout()
            } else{
                binding.listAppliedEvents.isVisible = true
                binding.noApplied.isVisible = false
                val params = binding.badgesText.layoutParams as ConstraintLayout.LayoutParams
                params.topToBottom = binding.listAppliedEvents.id
                params.topMargin = 0
                binding.badgesText.requestLayout()
                var applied_event_list: MutableList<EventModel> = mutableListOf()
                for(i in 0 until it.applied.size){
                    profileViewModel.getEventDetail(requireContext(), it.applied.get(i),
                        object: GetEventDetailListener() {
                            override fun onError() {
                                // error
                            }

                            override fun onResponse(eventModel: EventModel) {
                                applied_event_list.add(eventModel)
                                if(applied_event_list.size == it.applied.size){
                                    adapter_applied.eventList = applied_event_list
                                    adapter_applied.notifyDataSetChanged()
                                }
                            }
                        })
                }
            }
            if(it.badges_list.size == 0){
                binding.noBadges.isVisible = true
                binding.listBadges.isVisible = false
            } else{
                binding.listBadges.isVisible = true
                binding.noBadges.isVisible = false
                adapter_badges.badgeList = it.badges_list
                adapter_badges.notifyDataSetChanged()
            }
        })

        binding.btnSearchUser.setOnClickListener {
            if(binding.editTextSearchUser.text.toString() == ""){
                Toast.makeText(requireContext(),"Please don't leave the username blank!", Toast.LENGTH_SHORT).show()
            } else{
                profileViewModel.getProfile(requireContext(), binding.editTextSearchUser.text.toString(),
                    object: GetProfileListener() {
                        override fun onError(statusCode: Int?) {
                            if(statusCode == 404){
                                Toast.makeText(requireContext(),"There is no user with the given username!", Toast.LENGTH_SHORT).show()
                            } else{
                                Toast.makeText(requireContext(),"Something wrong. Please try again!", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onResponse(profileModel: ProfileModel) {
                            profile.value = profileModel
                        }
                    })
            }
        }

        return root
    }

}