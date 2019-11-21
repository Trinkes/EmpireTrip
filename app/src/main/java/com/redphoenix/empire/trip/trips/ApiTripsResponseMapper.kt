package com.redphoenix.empire.trip.trips

import java.io.IOException

class ApiTripsResponseMapper {
  fun mapSuccess(spaceTripEntities: List<TripsApi.SpaceTripEntity>): SpaceTrips {
    val spaceTrips = spaceTripEntities.map { SpaceTrip(it.id) }
    return SpaceTrips(SpaceTrips.Status.OK, spaceTrips)
  }

  fun mapError(error: Throwable): SpaceTrips {
    return when (error) {
      is IOException -> SpaceTrips(SpaceTrips.Status.NO_NETWORK, emptyList())
      else -> SpaceTrips(SpaceTrips.Status.ERROR, emptyList())
    }
  }
}
