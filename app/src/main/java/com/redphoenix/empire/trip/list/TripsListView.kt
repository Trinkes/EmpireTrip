package com.redphoenix.empire.trip.list

interface TripsListView {
    fun showTrips(trips: List<TripViewEntity>)
    fun showGenericError()
    fun showNoNetworkError()
    fun restoreTrips(trips: List<TripViewEntity>)

    data class TripViewEntity(
        val id: Int,
        val pilotName: String,
        val pilotAvatar: String,
        val pilotRating: Float,
        val pickUpLocation: String,
        val dropOffLocation: String
    )
}
