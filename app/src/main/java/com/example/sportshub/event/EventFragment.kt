package com.example.sportshub.event

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.sportshub.R
import com.example.sportshub.event.model.EventModel
import com.example.sportshub.databinding.FragmentEventBinding
import java.lang.Exception


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
        val root: View = binding.root

        val rw : RecyclerView = binding.listEvent
        val adapter = EventAdapter()
        rw.adapter = adapter
        eventViewModel.getAllEvents(requireContext(),
            object: EventListListener() {
                override fun onError() {
                    // error
                }

                override fun onResponse(eventList:MutableList<EventModel>) {
                    adapter.eventList = eventList
                    adapter.notifyDataSetChanged()
                }
            })

        binding.btnSearchByLocation.setOnClickListener{
            try {
                adapter.eventList = eventViewModel.searchByLocation(binding.editTextSearchEventLocation.text.toString())
                adapter.notifyDataSetChanged()
            }catch (e:Exception){
                Toast.makeText(requireContext(),"Error occurred: "+e.message,Toast.LENGTH_SHORT).show()
            }finally {
                binding.editTextSearchEventLocation.text.clear()
                val manager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(root.windowToken,0)
            }

        }

        binding.btnCreateEvent.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_event_to_eventCreateFragment)
        }

        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}