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




        return root
    }



}