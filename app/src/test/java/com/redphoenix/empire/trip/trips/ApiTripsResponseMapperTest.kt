package com.redphoenix.empire.trip.trips

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException


class ApiTripsResponseMapperTest {
    private lateinit var mapper: ApiTripsResponseMapper
    private val pilotName = "pilot_name"
    private val pilotAvatar = "avatar_url"
    private val pilotRating = 2.3f
    private val pickUpLocationName = "pick_up_location_name"
    private val dropOffLocationName = "drop_off_location_name"
    private val spaceTripEntity =
        TripsApi.SpaceTripEntity(
            1,
            TripsApi.Pilot(pilotName, pilotAvatar, pilotRating),
            TripsApi.Location(pickUpLocationName),
            TripsApi.Location(dropOffLocationName)
        )
    private val spaceTrip =
        SpaceTrip(1, pilotName, pilotAvatar, pilotRating, pickUpLocationName, dropOffLocationName)

    @Before
    fun setup() {
        mapper = ApiTripsResponseMapper()
    }

    @Test
    fun mapTripsFromApiResponse_Success() {
        val spaceTripEntities = listOf(spaceTripEntity)
        val spaceTrips = SpaceTripsResponse(ResponseStatus.OK, listOf(spaceTrip))
        Assert.assertEquals(spaceTrips, mapper.mapSuccess(spaceTripEntities))
    }

    @Test
    fun mapTripsFromApiResponse_NoNetwork() {
        val error = IOException("No internet connection")
        val spaceTrips = SpaceTripsResponse(ResponseStatus.NO_NETWORK, emptyList())
        Assert.assertEquals(spaceTrips, mapper.mapTripsError(error))
    }

    @Test
    fun mapTripsFromApiResponse_Error() {
        val error = Exception("Unknown error")
        val spaceTrips = SpaceTripsResponse(ResponseStatus.ERROR, emptyList())
        Assert.assertEquals(spaceTrips, mapper.mapTripsError(error))
    }

    @Test
    fun mapTripFromApiResponse_Success() {
        val spaceTrips = SpaceTripResponse(ResponseStatus.OK, spaceTrip)
        Assert.assertEquals(spaceTrips, mapper.mapSuccess(spaceTripEntity))
    }

    @Test
    fun mapTripFromApiResponse_NoNetwork() {
        val error = IOException("Unknown error")
        val spaceTrips = SpaceTripResponse(ResponseStatus.NO_NETWORK, null)
        Assert.assertEquals(spaceTrips, mapper.mapTripError(error))
    }

    @Test
    fun mapTripFromApiResponse_Error() {
        val error = Exception("Unknown error")
        val spaceTrips = SpaceTripResponse(ResponseStatus.ERROR, null)
        Assert.assertEquals(spaceTrips, mapper.mapTripError(error))
    }

}