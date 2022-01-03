package com.example.sportshub.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportshub.R
import com.example.sportshub.profile.model.BadgeModel

class BadgeAdapter: RecyclerView.Adapter<BadgeAdapter.BadgeViewHolder>() {
    var badgeList: MutableList<BadgeModel> = mutableListOf()

    class BadgeViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private val image: ImageView = view.findViewById(R.id.badge_pic)
        private val type: TextView = view.findViewById(R.id.badge_type)
        private val giver: TextView = view.findViewById(R.id.badge_giver)
        fun bind(badgeModel: BadgeModel){
            type.text = badgeModel.type
            giver.text = "${badgeModel.giver} granted this badge"
            if(badgeModel.type == "Skilled"){
                image.setImageResource(R.drawable.ic_skilled)
            } else if(badgeModel.type == "Friendly"){
                image.setImageResource(R.drawable.ic_friendly)
            } else if(badgeModel.type == "Master"){
                image.setImageResource(R.drawable.ic_master)
            } else if(badgeModel.type == "Novice"){
                image.setImageResource(R.drawable.ic_novice)
            } else if(badgeModel.type == "Top Organizer"){
                image.setImageResource(R.drawable.ic_top_organizer)
            } else if(badgeModel.type == "Sore Loser"){
                image.setImageResource(R.drawable.ic_sore_loser)
            } else if(badgeModel.type == "Crybaby"){
                image.setImageResource(R.drawable.ic_crybaby)
            } else if(badgeModel.type == "Gentleman"){
                image.setImageResource(R.drawable.ic_gentleman)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BadgeAdapter.BadgeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.badge_item, parent, false)
        return BadgeAdapter.BadgeViewHolder(view)
    }

    override fun onBindViewHolder(holder: BadgeAdapter.BadgeViewHolder, position: Int) {
        holder.bind(badgeList[position])

    }

    override fun getItemCount(): Int {
        return badgeList.size
    }

}