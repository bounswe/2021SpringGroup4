package com.example.sportshub.event

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TimePicker
import com.example.sportshub.R

class EventCreateFragment : Fragment() {

    companion object {
        fun newInstance() = EventCreateFragment()
    }

    private lateinit var viewModel: EventCreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.event_create_fragment, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TimePicker>(R.id.editTextTime).setIs24HourView(true)
        view.findViewById<TimePicker>(R.id.editTextDuration).setIs24HourView(true)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EventCreateViewModel::class.java)

    }

}