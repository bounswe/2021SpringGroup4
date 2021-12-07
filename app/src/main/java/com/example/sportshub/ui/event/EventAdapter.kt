package com.example.sportshub.ui.event

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportshub.R
import com.example.sportshub.backend_connection.event.EventModel

class EventAdapter:RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    var eventList: MutableList<EventModel> = MutableList<EventModel>(0, init = {index -> EventModel()})

    class EventViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.event_title)
        fun bind(string: String){
            title.text = string
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_item, parent, false)
        return EventViewHolder(view)
        // TODO implement here
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(eventList[position].toString())
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

}