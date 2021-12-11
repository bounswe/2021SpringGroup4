package com.example.sportshub.ui.event

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportshub.R
import com.example.sportshub.backend_connection.event.EventModel

class EventAdapter:RecyclerView.Adapter<EventAdapter.EventViewHolder>() {
    var eventList: MutableList<EventModel> = mutableListOf()

    class EventViewHolder(view: View):RecyclerView.ViewHolder(view) {
        var months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
        private val title: TextView = view.findViewById(R.id.event_title)
        private val location: TextView = view.findViewById(R.id.event_location)
        private val date: TextView = view.findViewById(R.id.event_date)
        private val time: TextView = view.findViewById(R.id.event_time)
        private val players: TextView = view.findViewById(R.id.event_players)
        private val creator: TextView = view.findViewById(R.id.event_creator)
        fun bind(eventModel: EventModel){
            title.text = eventModel.title
            location.text = eventModel.location
            val dateParse = eventModel.date.split("-")
            date.text = "${dateParse[2]} ${months[dateParse[1].toInt()-1]} ${dateParse[0]}"
            val timeParse = eventModel.time.split(":")
            time.text = "${timeParse[0]}:${timeParse[1]}"
            players.text = "${eventModel.participants.size} out of ${eventModel.maxPlayers} players are chosen"
            creator.text = "${eventModel.creator} created this event"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_item, parent, false)
        return EventViewHolder(view)
        // TODO implement here
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(eventList[position])
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

}