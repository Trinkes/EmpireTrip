package com.redphoenix.empire.trip.details

import com.redphoenix.empire.trip.components.ElapseTimeFormatter
import com.redphoenix.empire.trip.trips.ResponseStatus
import com.redphoenix.empire.trip.trips.SpaceTripResponse
import com.redphoenix.empire.trip.trips.Trips
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class TripDetailsPresenter(
    private val view: TripDetailsView,
    private val trips: Trips,
    private val viewScheduler: Scheduler,
    private val elapseTimeFormatter: ElapseTimeFormatter
) {
    private val disposables = CompositeDisposable()

    fun present(tripId: Int) {
        setupView(tripId)
        handleUpNavigationClick()
        handleRetryClick(tripId)
    }

    private fun handleRetryClick(tripId: Int) {
        disposables.add(view.getRetryClick().flatMap { fillView(tripId) }.subscribe())
    }

    private fun setupView(tripId: Int) {
        disposables.add(
            fillView(tripId)
                .subscribe()
        )

    }

    private fun fillView(tripId: Int): Observable<SpaceTripResponse> {
        return trips.getSpaceTrip(tripId)
            .observeOn(viewScheduler)
            .doOnSubscribe { view.showLoading() }
            .doOnNext {
                showTripDetails(it)
            }
    }

    private fun handleUpNavigationClick() {
        disposables.add(view.getUpNavigationClicks().subscribe { view.navigateBack() })
    }

    private fun showTripDetails(tripResponse: SpaceTripResponse) {
        when (tripResponse.status) {
            ResponseStatus.OK -> view.showTripDetails(
                tripResponse.trip!!.pilotName,
                tripResponse.trip.pilotAvatar,
                tripResponse.trip.pilotRating,
                tripResponse.trip.pickupLocation,
                tripResponse.trip.pickupTime,
                tripResponse.trip.pickupLocationIcon,
                tripResponse.trip.dropOffLocation,
                tripResponse.trip.dropOffTime,
                tripResponse.trip.dropOffLocationIcon,
                tripResponse.trip.tripDistance,
                tripResponse.trip.TripDistanceUnit,
                elapseTimeFormatter.format(tripResponse.trip.duration)
            )
            ResponseStatus.ERROR -> view.showGenericError()
            ResponseStatus.NO_NETWORK -> view.showNetworkError()
        }
    }

    fun stop() {
        disposables.clear()
    }

}
