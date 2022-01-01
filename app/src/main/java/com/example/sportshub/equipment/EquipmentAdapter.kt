package com.example.sportshub.equipment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sportshub.R
import com.example.sportshub.equipment.model.EquipmentModel
import com.example.sportshub.event.EventAdapter

class EquipmentAdapter:RecyclerView.Adapter<EquipmentAdapter.EquipmentViewHolder>() {
    var equipmentList : MutableList<EquipmentModel> = mutableListOf()

    class EquipmentViewHolder(view : View):RecyclerView.ViewHolder(view){
        private val title: TextView = view.findViewById(R.id.equipment_title)
        private val description: TextView = view.findViewById(R.id.equipment_description)
        private val owner: TextView = view.findViewById(R.id.equipment_owner)
        private val contact: TextView = view.findViewById(R.id.equipment_contact)
        private val location: TextView = view.findViewById(R.id.equipment_location)
        private val sportType: TextView = view.findViewById(R.id.equipment_sport_type)
        fun bind(equipmentModel: EquipmentModel) {
            title.text = equipmentModel.title
            description.text = equipmentModel.description
            owner.text = equipmentModel.owner
            contact.text = equipmentModel.contact
            location.text = equipmentModel.lcoation
            sportType.text = equipmentModel.sportType
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.equipment_item, parent, false)
        return EquipmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: EquipmentViewHolder, position: Int) {
        holder.bind(equipmentList[position])
    }

    override fun getItemCount(): Int {
        return equipmentList.size
    }

}