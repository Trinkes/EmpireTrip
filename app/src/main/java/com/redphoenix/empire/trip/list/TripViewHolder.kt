package com.redphoenix.empire.trip.list

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.redphoenix.empire.trip.R

class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val driverName: TextView = itemView.findViewById(R.id.pilot_name)

    fun bind(item: TripsListView.TripViewEntity) {
        driverName.text = item.pilotName
    }

}
