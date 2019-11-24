package com.redphoenix.empire.trip.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding2.view.RxView
import com.redphoenix.empire.trip.BuildConfig
import com.redphoenix.empire.trip.EmpireTripApplication
import com.redphoenix.empire.trip.R
import com.redphoenix.empire.trip.components.ElapseTimeFormatter
import com.redphoenix.empire.trip.components.getService
import com.redphoenix.empire.trip.trips.Trips
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_trip_details.*
import kotlinx.android.synthetic.main.fragment_trip_details_toolbar.*
import kotlinx.android.synthetic.main.generic_error_layout.*
import kotlinx.android.synthetic.main.network_error_layout.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TripDetailsFragment : Fragment(), TripDetailsView {
    companion object {
        private const val TRIP_ID_KEY = "TRIP_ID"
        fun newInstance(tripId: Int): TripDetailsFragment {
            return TripDetailsFragment().apply {
                arguments = bundleOf(Pair(TRIP_ID_KEY, tripId))
            }
        }
    }

    @Inject
    lateinit var trips: Trips

    @Inject
    lateinit var timeFormatter: SimpleDateFormat
    @Inject
    lateinit var elapseTimeFormatter: ElapseTimeFormatter
    private var upNavigationClicks: PublishSubject<Any> =
        PublishSubject.create()
    private lateinit var navigator: TripDetailsNavigator

    private lateinit var presenter: TripDetailsPresenter
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as EmpireTripApplication).appComponent.inject(this)
        navigator = getService()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter =
            TripDetailsPresenter(this, trips, AndroidSchedulers.mainThread(), elapseTimeFormatter)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trip_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.present(arguments!!.getInt(TRIP_ID_KEY))
        toolbar.setNavigationOnClickListener { upNavigationClicks.onNext(Any()) }
    }

    override fun getUpNavigationClicks(): Observable<Any> {
        return upNavigationClicks
    }

    override fun navigateBack() {
        navigator.navigateBack()
    }

    override fun showGenericError() {
        showView(ViewsGroup.GENERIC_ERROR_VIEW)
    }

    override fun showNetworkError() {
        showView(ViewsGroup.NETWORK_ERROR_VIEW)
    }

    override fun showTripDetails(
        pilotName: String,
        pilotAvatar: String,
        pilotRating: Float?,
        pickupLocation: String,
        pickUpTime: Long,
        pickupLocationIcon: String,
        dropOffLocation: String,
        dropOffTime: Long,
        dropOffLocationIcon: String,
        tripDistance: Long,
        tripDistanceUnit: String,
        tripDuration: String
    ) {
        Glide.with(this)
            .load("${BuildConfig.BASE_HOST}${pilotAvatar}")
            .into(fragment_details_pilot_avatar)
        Glide.with(this)
            .load("${BuildConfig.BASE_HOST}${pickupLocationIcon}")
            .into(pickup_location_image)
        Glide.with(this)
            .load("${BuildConfig.BASE_HOST}${dropOffLocationIcon}")
            .into(drop_off_location_image)
        fragment_details_pilot_name.text = pilotName
        fragment_details_pick_up_location_name.text = pickupLocation
        fragment_details_drop_off_location_name.text = dropOffLocation
        fragment_details_pick_up_time.text = timeFormatter.format(Date(pickUpTime))
        fragment_details_drop_off_time.text = timeFormatter.format(Date(dropOffTime))
        if (pilotRating == null) {
            fragment_details_pilot_no_rating.visibility = View.VISIBLE
            fragment_details_pilot_rating.visibility = View.GONE
        } else {
            fragment_details_pilot_rating.rating = pilotRating
            fragment_details_pilot_no_rating.visibility = View.GONE
            fragment_details_pilot_rating.visibility = View.VISIBLE
        }

        fragment_details_distance.text =
            getString(
                R.string.fragment_details_distance_number,
                NumberFormat.getInstance().format(tripDistance),
                tripDistanceUnit
            )
        fragment_details_duration.text = tripDuration
        showView(ViewsGroup.TRIP_DETAILS_VIEW)
    }

    override fun showLoading() {
        showView(ViewsGroup.LOADING_VIEW)
    }

    override fun onDestroyView() {
        presenter.stop()
        super.onDestroyView()
    }

    private fun showView(view: ViewsGroup) {
        when (view) {
            ViewsGroup.TRIP_DETAILS_VIEW -> {
                trip_details_view.visibility = View.VISIBLE
                generic_error_layout.visibility = View.GONE
                network_error_layout.visibility = View.GONE
                loading_layout.visibility = View.GONE
            }

            ViewsGroup.GENERIC_ERROR_VIEW -> {
                trip_details_view.visibility = View.GONE
                generic_error_layout.visibility = View.VISIBLE
                network_error_layout.visibility = View.GONE
                loading_layout.visibility = View.GONE
            }
            ViewsGroup.NETWORK_ERROR_VIEW -> {
                trip_details_view.visibility = View.GONE
                generic_error_layout.visibility = View.GONE
                network_error_layout.visibility = View.VISIBLE
                loading_layout.visibility = View.GONE
            }
            ViewsGroup.LOADING_VIEW -> {
                loading_layout.visibility = View.VISIBLE
                trip_details_view.visibility = View.GONE
                generic_error_layout.visibility = View.GONE
                network_error_layout.visibility = View.GONE
            }
        }
    }

    override fun getRetryClick(): Observable<Any> {
        return Observable.merge(
            RxView.clicks(generic_error_retry_button),
            RxView.clicks(no_network_retry_button)
        )

    }

    enum class ViewsGroup {
        TRIP_DETAILS_VIEW, GENERIC_ERROR_VIEW, NETWORK_ERROR_VIEW, LOADING_VIEW
    }
}
