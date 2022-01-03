package com.example.sportshub.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sportshub.LoginActivity
import com.example.sportshub.MainActivity
import com.example.sportshub.R
import com.example.sportshub.SingletonRequestQueueProvider
import com.example.sportshub.databinding.FragmentProfileBinding
import com.example.sportshub.event.AddCommentListener
import com.example.sportshub.event.EventDetailFragmentArgs
import com.example.sportshub.event.model.EventModel
import com.example.sportshub.profile.model.ProfileModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<ProfileFragmentArgs>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        fun visibility(button: Button, visible: Boolean){
            button.isEnabled = visible
            button.isClickable = visible
            button.isVisible = visible
        }

        fun visibilityFAB(button: FloatingActionButton, visible: Boolean){
            button.isEnabled = visible
            button.isClickable = visible
            button.isVisible = visible
        }

        fun visibilitySpinner(spinner: Spinner, visible: Boolean){
            spinner.isEnabled = visible
            spinner.isClickable = visible
            spinner.isVisible = visible
        }

        val profile = MutableLiveData<ProfileModel>()

        var currentProfile: String
        if(args.username == ""){
            currentProfile = SingletonRequestQueueProvider.getUsername()
        } else{
            currentProfile = args.username
        }

        profileViewModel.getProfile(requireContext(), currentProfile,
            object: GetProfileListener() {
                override fun onError(statusCode: Int?) {
                    // error
                }

                override fun onResponse(profileModel: ProfileModel) {
                    profile.value = profileModel
                }
            })

        val spinner_badge: Spinner = binding.spinnerBadgeType
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.badge_types,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_badge.adapter = it
        }

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
            visibilitySpinner(binding.spinnerBadgeType, false)
            visibilityFAB(binding.btnSendBadge, false)
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
                val params1 = binding.btnGrantBadge.layoutParams as ConstraintLayout.LayoutParams
                params1.topToBottom = binding.noBadges.id
                params1.topMargin = 64
                binding.badgesText.requestLayout()
                val params2 = binding.spinnerBadgeType.layoutParams as ConstraintLayout.LayoutParams
                params2.topToBottom = binding.noBadges.id
                params2.topMargin = 64
                binding.badgesText.requestLayout()
                val params3 = binding.btnSendBadge.layoutParams as ConstraintLayout.LayoutParams
                params3.topToBottom = binding.noBadges.id
                params3.topMargin = 64
                binding.badgesText.requestLayout()
            } else{
                binding.listBadges.isVisible = true
                binding.noBadges.isVisible = false
                val params1 = binding.btnGrantBadge.layoutParams as ConstraintLayout.LayoutParams
                params1.topToBottom = binding.listBadges.id
                params1.topMargin = 0
                binding.badgesText.requestLayout()
                val params2 = binding.spinnerBadgeType.layoutParams as ConstraintLayout.LayoutParams
                params2.topToBottom = binding.listBadges.id
                params2.topMargin = 0
                binding.badgesText.requestLayout()
                val params3 = binding.btnSendBadge.layoutParams as ConstraintLayout.LayoutParams
                params3.topToBottom = binding.listBadges.id
                params3.topMargin = 0
                binding.badgesText.requestLayout()
                adapter_badges.badgeList = it.badges_list
                adapter_badges.notifyDataSetChanged()
            }
            if(it.username == SingletonRequestQueueProvider.getUsername()){
                visibility(binding.btnGrantBadge, false)
            } else{
                visibility(binding.btnGrantBadge, true)
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
                            val action : NavDirections = ProfileFragmentDirections.actionNavigationProfileToNavigationProfile().setUsername(profileModel.username)
                            findNavController().navigate(action)
                        }
                    })
            }
        }

        binding.btnGrantBadge.setOnClickListener {
            visibility(binding.btnGrantBadge, false)
            visibilitySpinner(binding.spinnerBadgeType, true)
            visibilityFAB(binding.btnSendBadge, true)
        }

        binding.btnSendBadge.setOnClickListener {
            if(binding.spinnerBadgeType.selectedItem.toString() == "--Badge Type--"){
                Toast.makeText(requireContext(),"Badge type is required!", Toast.LENGTH_SHORT).show()
            } else{
                profileViewModel.grantBadge(requireContext(), profile.value!!.username, 1, binding.spinnerBadgeType.selectedItem.toString(),
                    object: GrantBadgeListener() {
                        override fun onError() {
                            Toast.makeText(requireContext(),"Something wrong. Please try again!", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse() {
                            Toast.makeText(requireContext(),"Badge granted successfully!", Toast.LENGTH_SHORT).show()
                            visibility(binding.btnGrantBadge, true)
                            visibilitySpinner(binding.spinnerBadgeType, false)
                            visibilityFAB(binding.btnSendBadge, false)
                        }
                    })
            }
        }

        binding.btnLogout.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        return root
    }

}