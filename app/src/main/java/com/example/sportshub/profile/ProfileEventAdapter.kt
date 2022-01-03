package com.example.sportshub.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.sportshub.R
import com.example.sportshub.event.EventFragmentDirections
import com.example.sportshub.event.model.EventModel

class ProfileEventAdapter: RecyclerView.Adapter<ProfileEventAdapter.ProfileEventViewHolder>() {
    var eventList: MutableList<EventModel> = mutableListOf()

    class ProfileEventViewHolder(view: View):RecyclerView.ViewHolder(view) {
        var months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
        private val title: TextView = view.findViewById(R.id.profile_event_title)
        private val location: TextView = view.findViewById(R.id.profile_event_location)
        private val date: TextView = view.findViewById(R.id.profile_event_date)
        private val time: TextView = view.findViewById(R.id.profile_event_time)
        fun bind(eventModel: EventModel){
            title.text = eventModel.title
            location.text = eventModel.location
            val dateParse = eventModel.date.split("-")
            date.text = "${dateParse[2]} ${months[dateParse[1].toInt()-1]} ${dateParse[0]}"
            val timeParse = eventModel.time.split(":")
            time.text = "${timeParse[0]}:${timeParse[1]}"
            itemView.setOnClickListener {
                val action : NavDirections = ProfileFragmentDirections.actionNavigationProfileToEventDetailFragment(eventModel)
                Navigation.findNavController(itemView).navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileEventAdapter.ProfileEventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.profile_event_item, parent, false)
        return ProfileEventAdapter.ProfileEventViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileEventAdapter.ProfileEventViewHolder, position: Int) {
        holder.bind(eventList[position])

    }

    override fun getItemCount(): Int {
        return eventList.size
    }

}