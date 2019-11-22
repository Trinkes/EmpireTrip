package com.redphoenix.empire.trip.trips

import io.reactivex.Single
import retrofit2.http.GET

interface TripsApi {

    @GET("trips")
    fun getSpaceTrips(): Single<List<SpaceTripEntity>>

    fun getSpaceTrip(tripId: Int): Single<SpaceTripEntity>

    data class SpaceTripEntity(
        val id: Int, val pilot: Pilot,
        val pick_up: Location,
        val drop_off: Location
    )

    data class Pilot(
        val name: String,
        val avatar: String,
        val rating: Float
    )

    data class Location(val name: String)
}
