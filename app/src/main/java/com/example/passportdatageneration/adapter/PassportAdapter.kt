package com.example.passportdatageneration.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.passportdatageneration.databinding.ItemNationBinding
import com.example.passportdatageneration.room.entity.Nations
import com.example.passportdatageneration.utils.Nation

class PassportAdapter(var listNation: List<Nations>, var onMyClickListener: OnMyClickListener) : RecyclerView.Adapter<PassportAdapter.Vh>() {
    inner class Vh(var itemNation: ItemNationBinding) : RecyclerView.ViewHolder(itemNation.root) {

        fun onBind(nations: Nations) {
            itemNation.nationName.text = "${adapterPosition + 1}. ${nations.lastName} ${nations.name}"
            itemNation.passportId.text = nations.passportTime

            itemNation.nationItem.setOnClickListener {
                onMyClickListener.onMyClick(adapterPosition, nations)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        val itemView = ItemNationBinding .inflate(LayoutInflater.from(parent.context), parent, false)
        return Vh(itemView)
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(listNation[position])
    }

    override fun getItemCount(): Int {
        return listNation.size
    }

    interface OnMyClickListener {
        fun onMyClick(position: Int, nation: Nations)
    }
}