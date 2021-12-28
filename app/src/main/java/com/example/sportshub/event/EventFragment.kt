package com.example.sportshub.event

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.sportshub.R
import com.example.sportshub.event.model.EventModel
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

        var dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.search_event_dialog)
        dialog.window!!.setBackgroundDrawable(getDrawable(requireContext(),R.drawable.dialog_drawable_background))
        dialog.window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT)

        dialog.findViewById<Button>(R.id.btn_cancel_search).setOnClickListener {
            dialog.cancel()
        }

        binding.findByCreator.setOnClickListener {
            dialog.findViewById<TextView>(R.id.search_dialog_title).text = "Find by Creator"
            dialog.show()
            dialog.findViewById<Button>(R.id.btn_confirm_search).setOnClickListener {
                eventViewModel.searchByCreator(dialog.findViewById<EditText>(R.id.edit_text_search_dialog).text.toString())
            }
        }

        binding.findByType.setOnClickListener {
            dialog.findViewById<TextView>(R.id.search_dialog_title).text = "Find by Sport Type"
            dialog.show()
            dialog.findViewById<Button>(R.id.btn_confirm_search).setOnClickListener {
                eventViewModel.searchByType(dialog.findViewById<EditText>(R.id.edit_text_search_dialog).text.toString())
            }
            //TODO
        }

        binding.findByLevel.setOnClickListener {
            dialog.findViewById<TextView>(R.id.search_dialog_title).text = "Find by Skill Level"
            dialog.show()
            dialog.findViewById<Button>(R.id.btn_confirm_search).setOnClickListener {
                eventViewModel.searchByLevel(dialog.findViewById<EditText>(R.id.edit_text_search_dialog).text.toString())
            }
            //TODO
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