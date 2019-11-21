package com.redphoenix.empire.trip.trips

import io.reactivex.Observable

interface TripsRepository {
  fun getSpaceTrips(): Observable<SpaceTripsResponse>
  fun getSpaceTrip(tripId: Int): Observable<SpaceTripResponse>
}
