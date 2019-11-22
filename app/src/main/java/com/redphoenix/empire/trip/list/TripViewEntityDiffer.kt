package com.redphoenix.empire.trip.list

import androidx.recyclerview.widget.DiffUtil

class TripViewEntityDiffer : DiffUtil.ItemCallback<TripsListView.TripViewEntity>() {
    override fun areItemsTheSame(
        oldItem: TripsListView.TripViewEntity,
        newItem: TripsListView.TripViewEntity
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: TripsListView.TripViewEntity,
        newItem: TripsListView.TripViewEntity
    ): Boolean {
        return oldItem.id == newItem.id
                && oldItem.pilotName == newItem.pilotName
                && oldItem.pilotAvatar == newItem.pilotAvatar
                && oldItem.pilotRating == newItem.pilotRating
                && oldItem.pickUpLocation == newItem.pickUpLocation
                && oldItem.dropOffLocation == newItem.dropOffLocation
    }
}