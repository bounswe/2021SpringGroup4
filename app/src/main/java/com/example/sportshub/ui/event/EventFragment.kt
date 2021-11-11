package com.example.sportshub.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.sportshub.R
import com.example.sportshub.databinding.FragmentEventBinding


class EventFragment : Fragment() {

    private lateinit var eventViewModel: EventViewModel
    private var _binding: FragmentEventBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        eventViewModel =
                ViewModelProvider(this).get(EventViewModel::class.java)

        _binding = FragmentEventBinding.inflate(inflater, container, false)

        val rw : RecyclerView = binding.listEvent
        rw.adapter = EventAdapter()

        val root: View = binding.root

        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}