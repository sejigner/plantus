package com.example.plantus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plantus.dataClass.PlantIndex
import com.example.plantus.databinding.ListRowPlantBinding

class PlantListViewHolder(val binding: ListRowPlantBinding) : RecyclerView.ViewHolder(binding.root)
class PlantListAdapter(private val itemList: MutableList<PlantIndex>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantListViewHolder {
        return PlantListViewHolder(ListRowPlantBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val bindingSub = (holder as PlantListViewHolder).binding
        bindingSub.itemPlantName.text = itemList[position].name
    }
}