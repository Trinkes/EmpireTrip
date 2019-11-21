package com.redphoenix.empire.trip.trips

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class ApiTripsResponseMapperTest {
  lateinit var mapper: ApiTripsResponseMapper

  @Before
  fun setup() {
    mapper = ApiTripsResponseMapper()
  }

  @Test
  fun mapTripsFromApiResponse_Success() {
    val spaceTripEntities = listOf(TripsApi.SpaceTripEntity(1))
    val spaceTrips = SpaceTrips(SpaceTrips.Status.OK, listOf(SpaceTraveler(1)))
    Assert.assertEquals(spaceTrips, mapper.mapSuccess(spaceTripEntities))
  }

  @Test
  fun mapTripsFromApiResponse_NoNetwork() {
    val error = IOException("No internet connection")
    val spaceTrips = SpaceTrips(SpaceTrips.Status.NO_NETWORK, emptyList())
    Assert.assertEquals(spaceTrips, mapper.mapError(error))
  }

  @Test
  fun mapTripsFromApiResponse_Error() {
    val error = Exception("Unknown error")
    val spaceTrips = SpaceTrips(SpaceTrips.Status.ERROR, emptyList())
    Assert.assertEquals(spaceTrips, mapper.mapError(error))
  }
}