package com.redphoenix.empire.trip.trips

data class SpaceTrip(
    val id: Int,
    val pilotName: String,
    val pilotAvatar: String,
    val pilotRating: Float,
    val pickupLocation: String,
    val pickupTime: Long,
    val pickupLocationIcon: String,
    val dropOffLocation: String,
    val dropOffLocationIcon: String,
    val dropOffTime: Long
)
