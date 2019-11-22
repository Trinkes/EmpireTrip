package com.redphoenix.empire.trip.trips

import io.reactivex.Observable

class NetworkTripsRepository(private val api: TripsApi,
                             private val mapper: ApiTripsResponseMapper) :
    TripsRepository {
  override fun getSpaceTrips(): Observable<SpaceTripsResponse> {
    return api.getSpaceTrips()
        .map { mapper.mapSuccess(it) }
        .doOnError { it.printStackTrace() }
        .onErrorReturn { mapper.mapTripsError(it) }
        .toObservable()
  }

  override fun getSpaceTrip(tripId: Int): Observable<SpaceTripResponse> {
    return api.getSpaceTrip(tripId)
        .map { mapper.mapSuccess(it) }
        .doOnError { it.printStackTrace() }
        .onErrorReturn { mapper.mapTripError(it) }
        .toObservable()
  }
}