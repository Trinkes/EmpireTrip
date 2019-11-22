package com.redphoenix.empire.trip.trips

import io.reactivex.Observable
import io.reactivex.Scheduler

class Trips(private val scheduler: Scheduler, private val repository: TripsRepository) {
    fun getSpaceTrips(): Observable<SpaceTripsResponse> {
        return repository.getSpaceTrips()
            .subscribeOn(scheduler)
    }

    fun getSpaceTrip(tripId: Int): Observable<SpaceTripResponse> {
        return repository.getSpaceTrip(tripId)
            .subscribeOn(scheduler)
    }
}