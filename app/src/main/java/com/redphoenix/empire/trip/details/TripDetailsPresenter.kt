package com.redphoenix.empire.trip.details

import com.redphoenix.empire.trip.components.ElapseTimeFormatter
import com.redphoenix.empire.trip.trips.ResponseStatus
import com.redphoenix.empire.trip.trips.SpaceTripResponse
import com.redphoenix.empire.trip.trips.Trips
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
    }

    private fun setupView(tripId: Int) {
        disposables.add(trips.getSpaceTrip(tripId)
            .observeOn(viewScheduler)
            .doOnNext {
                showTripDetails(it)
            }
            .subscribe())

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
            ResponseStatus.ERROR -> TODO()
            ResponseStatus.NO_NETWORK -> TODO()
        }
    }

    fun stop() {
        disposables.clear()
    }

}
