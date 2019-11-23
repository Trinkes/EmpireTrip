package com.redphoenix.empire.trip.details

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
        dropOffLocationIcon: String
    )

}
