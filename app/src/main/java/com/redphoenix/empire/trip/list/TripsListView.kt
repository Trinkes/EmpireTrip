package com.redphoenix.empire.trip.list

interface TripsListView {
    fun showTrips(trips: List<TripViewEntity>)
    fun showGenericError()
    fun showNoNetworkError()

    data class TripViewEntity(val id: Int)
}
