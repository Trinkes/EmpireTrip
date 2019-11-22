package com.redphoenix.empire.trip.trips

import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class NetworkTripsRepositoryTest {

    @Mock
    lateinit var api: TripsApi
    private lateinit var repository: NetworkTripsRepository

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
        repository = NetworkTripsRepository(api, ApiTripsResponseMapper())
    }

    @Test
    fun getSpaceTrips_Success() {
        val spaceTripsResponse = listOf(spaceTripEntity)
        `when`(api.getSpaceTrips()).thenReturn(Single.just(spaceTripsResponse))
        val tripsTest = repository.getSpaceTrips()
            .test()
        tripsTest.awaitTerminalEvent()
        tripsTest.assertValue(SpaceTripsResponse(ResponseStatus.OK, listOf(spaceTrip)))
            .assertNoErrors()
    }

    @Test
    fun getSpaceTrips_NetworkError() {
        `when`(api.getSpaceTrips()).thenReturn(Single.error(IOException("No Internet Connection")))
        val tripsTest = repository.getSpaceTrips()
            .test()
        tripsTest.awaitTerminalEvent()
        tripsTest.assertValue(SpaceTripsResponse(ResponseStatus.NO_NETWORK, emptyList()))
            .assertNoErrors()
    }

    @Test
    fun getSpaceTrips_Error() {
        `when`(api.getSpaceTrips()).thenReturn(Single.error(Exception("Unknown error")))
        val tripsTest = repository.getSpaceTrips()
            .test()
        tripsTest.awaitTerminalEvent()
        tripsTest.assertValue(SpaceTripsResponse(ResponseStatus.ERROR, emptyList()))
            .assertNoErrors()
    }

    @Test
    fun getSpaceTrip_Success() {
        `when`(api.getSpaceTrip(1)).thenReturn(Single.just(spaceTripEntity))
        val tripsTest = repository.getSpaceTrip(1)
            .test()
        tripsTest.awaitTerminalEvent()
        tripsTest.assertValue(SpaceTripResponse(ResponseStatus.OK, spaceTrip))
            .assertNoErrors()
    }

    @Test
    fun getSpaceTrip_NetworkError() {
        `when`(api.getSpaceTrip(1)).thenReturn(Single.error(IOException("No Internet Connection")))
        val tripsTest = repository.getSpaceTrip(1)
            .test()
        tripsTest.awaitTerminalEvent()
        tripsTest.assertValue(SpaceTripResponse(ResponseStatus.NO_NETWORK, null))
            .assertNoErrors()
    }

    @Test
    fun getSpaceTrip_Error() {
        `when`(api.getSpaceTrip(1)).thenReturn(Single.error(Exception("Unknown Error")))
        val tripsTest = repository.getSpaceTrip(1)
            .test()
        tripsTest.awaitTerminalEvent()
        tripsTest.assertValue(SpaceTripResponse(ResponseStatus.ERROR, null))
            .assertNoErrors()
    }
}