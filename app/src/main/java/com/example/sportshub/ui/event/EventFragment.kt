package com.example.sportshub.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.sportshub.R
import java.lang.Exception
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
        val adapter = EventAdapter()
        rw.adapter = adapter
        adapter.eventList = eventViewModel.getAllEvents()
        adapter.notifyDataSetChanged()

        binding.btnSearchByLocation.setOnClickListener{
            try {
                adapter.eventList = eventViewModel.searchByLocation(binding.editTextSearchEventLocation.text.toString())
                adapter.notifyDataSetChanged()
            }catch (e:Exception){
                Toast.makeText(requireContext(),"Error occurred: "+e.message,Toast.LENGTH_SHORT).show()
            }finally {
                binding.editTextSearchEventLocation.text.clear()

            }

        }


        val root: View = binding.root

        return root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}