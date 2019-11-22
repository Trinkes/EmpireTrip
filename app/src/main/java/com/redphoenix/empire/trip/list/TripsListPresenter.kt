package com.redphoenix.empire.trip.list

import com.redphoenix.empire.trip.trips.ResponseStatus
import com.redphoenix.empire.trip.trips.SpaceTripsResponse
import com.redphoenix.empire.trip.trips.Trips
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class TripsListPresenter(
    private val view: TripsListView,
    private val trips: Trips,
    private val viewScheduler: Scheduler,
    private val mapper: TripsViewMapper
) {
    private val disposables = CompositeDisposable()
    fun present(isCreatingView: Boolean) {
        handleTripsDataUpdate(isCreatingView)
        setupView()
        handleTripClicks()
    }

    private fun handleTripClicks() {
        disposables.add(view.getTripClicks().doOnNext { view.showTripDetails(it) }.subscribe())
    }

    private fun handleTripsDataUpdate(creatingView: Boolean) {
        disposables.add(trips.getSpaceTrips()
            .skip(1)
            .observeOn(viewScheduler)
            .doOnNext { showTrips(it, creatingView) }
            .subscribe())
    }

    private fun setupView() {
        disposables.add(trips.getSpaceTrips()
            .take(1)
            .observeOn(viewScheduler)
            .doOnNext { showTrips(it, false) }
            .subscribe())
    }

    private fun showTrips(tripsResponse: SpaceTripsResponse, isCreatingView: Boolean) {
        when (tripsResponse.status) {
            ResponseStatus.OK -> {
                if (isCreatingView) {
                    view.showTrips(mapper.map(tripsResponse.trips))
                } else {
                    view.restoreTrips(mapper.map(tripsResponse.trips))
                }
            }
            ResponseStatus.ERROR -> view.showGenericError()
            ResponseStatus.NO_NETWORK -> view.showNoNetworkError()
        }
    }

    fun stop() {
        disposables.clear()
    }
}
