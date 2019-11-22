package com.redphoenix.empire.trip.trips

import java.io.IOException

class ApiTripsResponseMapper {
    fun mapSuccess(spaceTripEntities: List<TripsApi.SpaceTripEntity>): SpaceTripsResponse {
        val spaceTrips = spaceTripEntities.map { map(it) }
        return SpaceTripsResponse(ResponseStatus.OK, spaceTrips)
    }

    private fun map(spaceTripEntity: TripsApi.SpaceTripEntity) =
        SpaceTrip(
            spaceTripEntity.id,
            spaceTripEntity.pilot.name,
            spaceTripEntity.pilot.avatar,
            spaceTripEntity.pilot.rating,
            spaceTripEntity.pick_up.name,
            spaceTripEntity.drop_off.name
        )

    fun mapTripsError(error: Throwable): SpaceTripsResponse {
        return when (error) {
            is IOException -> SpaceTripsResponse(
                ResponseStatus.NO_NETWORK, emptyList()
            )
            else -> SpaceTripsResponse(ResponseStatus.ERROR, emptyList())
        }
    }

    fun mapSuccess(spaceTripEntity: TripsApi.SpaceTripEntity): SpaceTripResponse {
        return SpaceTripResponse(ResponseStatus.OK, map(spaceTripEntity))
    }

    fun mapTripError(error: Throwable): SpaceTripResponse {
        return when (error) {
            is IOException -> SpaceTripResponse(
                ResponseStatus.NO_NETWORK, null
            )
            else -> SpaceTripResponse(ResponseStatus.ERROR, null)
        }
    }

}
