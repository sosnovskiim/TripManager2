package com.sosnowskydevelop.tripmanager2.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sosnowskydevelop.tripmanager2.data.Trip
import com.sosnowskydevelop.tripmanager2.databinding.ListItemTripBinding
import com.sosnowskydevelop.tripmanager2.fragments.TripListFragmentDirections

class TripListAdapter : ListAdapter<Trip, TripListAdapter.TripViewHolder>(Companion) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemTripBinding.inflate(layoutInflater)
        return TripViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val currentTrip = getItem(position)
        holder.itemView.setOnClickListener {
            holder.itemView.findNavController().navigate(
                TripListFragmentDirections.actionTripListFragmentToRefuelingListFragment(currentTrip.id)
            )
        }
        holder.binding.tripName.text = currentTrip.name
    }

    class TripViewHolder(val binding: ListItemTripBinding) : RecyclerView.ViewHolder(binding.root)

    companion object : DiffUtil.ItemCallback<Trip>() {
        override fun areItemsTheSame(oldItem: Trip, newItem: Trip): Boolean =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: Trip, newItem: Trip): Boolean =
            oldItem.id == newItem.id
    }
}