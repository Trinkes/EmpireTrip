package com.redphoenix.empire.trip.trips

/**
 * @param trip data about the requested trip. This parameter is null if an error occurs
 */
data class SpaceTripResponse(val status: ResponseStatus, val trip: SpaceTrip?)

