package com.redphoenix.empire.trip.details

import io.reactivex.Observable

interface TripDetailsView {
    fun showTripDetails(
        pilotName: String,
        pilotAvatar: String,
        pilotRating: Float?,
        pickupLocation: String,
        pickUpTime: Long,
        pickupLocationIcon: String,
        dropOffLocation: String,
        dropOffTime: Long,
        dropOffLocationIcon: String,
        tripDistance: Long,
        tripDistanceUnit: String,
        tripDuration: String
    )

    fun getUpNavigationClicks(): Observable<Any>
    fun navigateBack()
    fun showGenericError()
    fun showNetworkError()
    fun getRetryClick(): Observable<Any>

}
