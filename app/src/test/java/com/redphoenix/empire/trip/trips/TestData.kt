package com.redphoenix.empire.trip.trips

import java.util.*

private const val pilotName = "pilot_name"
private const val pilotAvatar = "avatar_url"
private const val pilotRating = 2.3f
private const val pickUpLocationName = "pick_up_location_name"
private const val pickUpLocationIcon = "pick_up_location_icon"
private const val pickUpTime = 1L
private const val dropOffLocationName = "drop_off_location_name"
private const val dropOffLocationIcon = "drop_off_location_icon"
private const val dropOffTime = 1L
val spaceTripEntity =
    TripsApi.SpaceTripEntity(
        1,
        TripsApi.Pilot(pilotName, pilotAvatar, pilotRating),
        TripsApi.Location(pickUpLocationName, Date(pickUpTime), pickUpLocationIcon),
        TripsApi.Location(dropOffLocationName, Date(dropOffTime), dropOffLocationIcon)
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
        dropOffTime
    )