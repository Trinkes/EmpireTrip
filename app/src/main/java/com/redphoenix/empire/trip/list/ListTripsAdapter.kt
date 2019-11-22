package com.redphoenix.empire.trip.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.redphoenix.empire.trip.R

class ListTripsAdapter(
    differ: TripViewEntityDiffer,
    private val inflater: LayoutInflater
) :
    ListAdapter<TripsListView.TripViewEntity, TripViewHolder>(differ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        return TripViewHolder(inflater.inflate(R.layout.item_list_trips, parent, false))
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun setTrips(trips: List<TripsListView.TripViewEntity>) {
        submitList(trips)
    }

}
