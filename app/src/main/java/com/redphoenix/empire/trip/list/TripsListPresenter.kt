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
    fun present() {
        handleTripsDataUpdate()
        setupView()
        handleTripClicks()
        handleRetryClick()
    }

    private fun handleRetryClick() {
        disposables.add(view.getRetryClicks().flatMap { fillView() }.subscribe())
    }

    private fun handleTripClicks() {
        disposables.add(view.getTripClicks().doOnNext { view.showTripDetails(it) }.subscribe())
    }

    private fun handleTripsDataUpdate() {
        disposables.add(trips.getSpaceTrips()
            .skip(1)
            .observeOn(viewScheduler)
            .doOnNext { showTrips(it) }
            .subscribe())
    }

    private fun setupView() {
        disposables.add(fillView().subscribe())
    }

    private fun fillView(): Observable<SpaceTripsResponse> {
        return trips.getSpaceTrips()
            .take(1)
            .observeOn(viewScheduler)
            .doOnSubscribe { view.showLoading() }
            .doOnNext { showTrips(it) }
    }

    private fun showTrips(tripsResponse: SpaceTripsResponse) {
        when (tripsResponse.status) {
            ResponseStatus.OK -> {
                view.showTrips(mapper.map(tripsResponse.trips))
            }
            ResponseStatus.ERROR -> view.showGenericError()
            ResponseStatus.NO_NETWORK -> view.showNoNetworkError()
        }
    }

    fun stop() {
        disposables.clear()
    }
}
