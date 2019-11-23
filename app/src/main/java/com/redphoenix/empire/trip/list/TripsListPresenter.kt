package com.redphoenix.empire.trip.list

import com.redphoenix.empire.trip.trips.ResponseStatus
import com.redphoenix.empire.trip.trips.SpaceTripsResponse
import com.redphoenix.empire.trip.trips.Trips
import io.reactivex.Observable
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
        handleTripsDataUpdate()
        setupView(isCreatingView)
        handleTripClicks()
        handleRetryClick()
    }

    private fun handleRetryClick() {
        disposables.add(view.getRetryClicks().flatMap { fillView(false) }.subscribe())
    }

    private fun handleTripClicks() {
        disposables.add(view.getTripClicks().doOnNext { view.showTripDetails(it) }.subscribe())
    }

    private fun handleTripsDataUpdate() {
        disposables.add(trips.getSpaceTrips()
            .skip(1)
            .observeOn(viewScheduler)
            .doOnNext { showTrips(it, false) }
            .subscribe())
    }

    private fun setupView(isCreatingView: Boolean) {
        disposables.add(fillView(isCreatingView).subscribe())
    }

    private fun fillView(isCreatingView: Boolean): Observable<SpaceTripsResponse> {
        return trips.getSpaceTrips()
            .take(1)
            .observeOn(viewScheduler)
            .doOnNext { showTrips(it, isCreatingView) }
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
