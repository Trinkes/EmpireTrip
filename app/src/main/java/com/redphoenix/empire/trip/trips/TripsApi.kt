package com.redphoenix.empire.trip.trips

import io.reactivex.Observable
import retrofit2.http.GET

interface TripsApi {

  @GET("trips")
  fun getSpaceTrips(): Observable<List<SpaceTripEntity>>

  data class SpaceTripEntity(val id: Int)
}
