package com.redphoenix.empire.trip.trips

import io.reactivex.Observable
import io.reactivex.Scheduler

class Trips(private val scheduler: Scheduler, private val repository: TripsRepository) {
  fun getSpaceTrips(): Observable<SpaceTrips> {
    return repository.getSpaceTrips()
        .subscribeOn(scheduler)
  }
}