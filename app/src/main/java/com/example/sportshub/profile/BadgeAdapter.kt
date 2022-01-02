package com.example.sportshub.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportshub.R
import com.example.sportshub.profile.model.BadgeModel

class BadgeAdapter: RecyclerView.Adapter<BadgeAdapter.BadgeViewHolder>() {
    var badgeList: MutableList<BadgeModel> = mutableListOf()

    class BadgeViewHolder(view: View):RecyclerView.ViewHolder(view) {
        private val type: TextView = view.findViewById(R.id.badge_type)
        private val giver: TextView = view.findViewById(R.id.badge_giver)
        fun bind(badgeModel: BadgeModel){
            type.text = badgeModel.type
            giver.text = "${badgeModel.giver} granted this badge"
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