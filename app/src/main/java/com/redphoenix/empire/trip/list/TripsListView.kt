package com.redphoenix.empire.trip.list

import io.reactivex.Observable

interface TripsListView {
    fun showTrips(trips: List<TripViewEntity>)
    fun showGenericError()
    fun showNoNetworkError()
    fun restoreTrips(trips: List<TripViewEntity>)
    fun getTripClicks(): Observable<Int>
    fun showTripDetails(tripId: Int)
    fun getRetryClicks(): Observable<Any>

    data class TripViewEntity(
        val id: Int,
        val pilotName: String,
        val pilotAvatar: String,
        val pilotRating: Float?,
        val pickUpLocation: String,
        val dropOffLocation: String
    )
}
