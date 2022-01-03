package com.example.sportshub.equipment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.sportshub.R
import com.example.sportshub.databinding.FragmentEquipmentBinding

class EquipmentFragment : Fragment() {

    private lateinit var equipmentViewModel: EquipmentViewModel
    private var _binding: FragmentEquipmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        equipmentViewModel =
                ViewModelProvider(this).get(EquipmentViewModel::class.java)

        _binding = FragmentEquipmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.equipmentList
        val adapter = EquipmentAdapter()
        recyclerView.adapter = adapter
        equipmentViewModel.getAllEquipments()

        equipmentViewModel.equipments.observe(viewLifecycleOwner,{
            adapter.equipmentList = it
            adapter.notifyDataSetChanged()
        })

        binding.btnFilterEquipmentBySportType.setOnClickListener {
            equipmentViewModel.searchEquipmentsbySportType(binding.editTextFilterEquipmentBySportType.text.toString())
            binding.editTextFilterEquipmentBySportType.text.clear()
        }

        binding.btnCreateEquipment.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_equipment_to_createEquipmentFragment)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}