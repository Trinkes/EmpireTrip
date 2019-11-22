package com.redphoenix.empire.trip.list

import com.redphoenix.empire.trip.trips.ResponseStatus
import com.redphoenix.empire.trip.trips.SpaceTripsResponse
import com.redphoenix.empire.trip.trips.Trips
import io.reactivex.Scheduler

class TripsListPresenter(
    private val view: TripsListView,
    private val trips: Trips,
    private val viewScheduler: Scheduler,
    private val mapper: TripsViewMapper
) {
  fun present() {
    setupView()
  }

  private fun setupView() {
    trips.getSpaceTrips()
        .observeOn(viewScheduler)
        .doOnNext { showTrips(it) }
        .subscribe()
  }

  private fun showTrips(tripsResponse: SpaceTripsResponse) {
    when (tripsResponse.status) {
      ResponseStatus.OK -> view.showTrips(mapper.map(tripsResponse.trips))
      ResponseStatus.ERROR -> view.showGenericError()
      ResponseStatus.NO_NETWORK -> view.showNoNetworkError()
    }
  }
}
