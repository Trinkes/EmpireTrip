package com.redphoenix.empire.trip.trips

import io.reactivex.Observable

class NetworkTripsRepository(private val api: TripsApi,
                             private val mapper: ApiTripsResponseMapper) :
    TripsRepository {
  override fun getSpaceTrips(): Observable<SpaceTrips> {
    return api.getSpaceTrips()
        .map { mapper.mapSuccess(it) }
        .onErrorReturn { mapper.mapError(it) }
  }
}