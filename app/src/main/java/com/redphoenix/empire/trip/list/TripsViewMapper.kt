package com.redphoenix.empire.trip.list

import com.redphoenix.empire.trip.trips.SpaceTrip

class TripsViewMapper {
    fun map(trips: List<SpaceTrip>): List<TripsListView.TripViewEntity> {
        return trips.map { TripsListView.TripViewEntity(it.id) }
    }

}
