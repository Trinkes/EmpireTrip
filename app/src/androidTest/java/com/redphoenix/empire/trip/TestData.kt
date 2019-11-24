package com.redphoenix.empire.trip

import com.redphoenix.empire.trip.trips.SpaceTrip
import com.redphoenix.empire.trip.trips.TripsApi
import java.util.*

const val pilotName = "pilot_name"
private const val pilotAvatar = "avatar_url"
private const val pilotRating = 2.3f
const val pickUpLocationName = "pick_up_location_name"
private const val pickUpLocationIcon = "pick_up_location_icon"
private const val pickUpTime = 1L
const val dropOffLocationName = "drop_off_location_name"
private const val dropOffLocationIcon = "drop_off_location_icon"
private const val dropOffTime = 1L
private const val tripDuration = 10L
private val tripDistance = TripsApi.Distance(2L, "KM")
val spaceTripEntity =
    TripsApi.SpaceTripEntity(
        1,
        TripsApi.Pilot(pilotName, pilotAvatar, pilotRating),
        TripsApi.Location(pickUpLocationName, Date(pickUpTime), pickUpLocationIcon),
        TripsApi.Location(dropOffLocationName, Date(dropOffTime), dropOffLocationIcon),
        TripsApi.Distance(2L, "KM"), tripDuration
    )
val spaceTrip =
    SpaceTrip(
        1,
        pilotName,
        pilotAvatar,
        pilotRating,
        pickUpLocationName,
        pickUpTime,
        pickUpLocationIcon,
        dropOffLocationName,
        dropOffLocationIcon,
        dropOffTime,
        tripDistance.value,
        tripDistance.unit,
        tripDuration
    )