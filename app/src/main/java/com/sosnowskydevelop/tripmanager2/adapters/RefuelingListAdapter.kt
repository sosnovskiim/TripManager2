package com.sosnowskydevelop.tripmanager2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sosnowskydevelop.tripmanager2.data.Refueling
import com.sosnowskydevelop.tripmanager2.databinding.ListItemRefuelingBinding
import com.sosnowskydevelop.tripmanager2.fragments.RefuelingListFragmentDirections

class RefuelingListAdapter :
    ListAdapter<Refueling, RefuelingListAdapter.RefuelingViewHolder>(Companion) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RefuelingViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemRefuelingBinding.inflate(layoutInflater)
        return RefuelingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RefuelingViewHolder, position: Int) {
        val currentRefueling = getItem(position)
        holder.itemView.setOnClickListener {
            holder.itemView.findNavController().navigate(
                RefuelingListFragmentDirections.actionRefuelingListFragmentToRefuelingDetailFragment(
                    tripId = currentRefueling.tripId,
                    refueling = currentRefueling
                )
            )
        }
        holder.binding.refuelingOdometer.text =
            currentRefueling.odometer
        holder.binding.refuelingLitersFilled.text =
            String.format("%.2f", currentRefueling.litersFilled.toFloat())
        holder.binding.refuelingPricePerLiter.text =
            String.format("%.2f", currentRefueling.pricePerLiter.toFloat())
        holder.binding.refuelingIsToFull.text =
            currentRefueling.isToFullCapitalize
    }

    class RefuelingViewHolder(val binding: ListItemRefuelingBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object : DiffUtil.ItemCallback<Refueling>() {
        override fun areItemsTheSame(oldItem: Refueling, newItem: Refueling): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: Refueling, newItem: Refueling): Boolean =
            oldItem.id == newItem.id
    }
}