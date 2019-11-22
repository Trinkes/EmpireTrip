package com.redphoenix.empire.trip.trips

import io.reactivex.Single
import retrofit2.http.GET

interface TripsApi {

    @GET("trips")
    fun getSpaceTrips(): Single<List<SpaceTripEntity>>

    fun getSpaceTrip(tripId: Int): Single<SpaceTripEntity>

    data class SpaceTripEntity(val id: Int)
}
