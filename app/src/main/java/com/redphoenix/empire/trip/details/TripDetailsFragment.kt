package com.redphoenix.empire.trip.details

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.redphoenix.empire.trip.BuildConfig
import com.redphoenix.empire.trip.EmpireTripApplication
import com.redphoenix.empire.trip.R
import com.redphoenix.empire.trip.trips.Trips
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.trip_details_fragment.*
import kotlinx.android.synthetic.main.trip_details_toolbar.*
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

    private lateinit var presenter: TripDetailsPresenter
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as EmpireTripApplication).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = TripDetailsPresenter(this, trips, AndroidSchedulers.mainThread())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.trip_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.present(arguments!!.getInt(TRIP_ID_KEY))
    }

    override fun showTripDetails(
        pilotName: String,
        pilotAvatar: String,
        pilotRating: Float,
        pickupLocation: String,
        pickUpTime: Long,
        pickupLocationIcon: String,
        dropOffLocation: String,
        dropOffTime: Long,
        dropOffLocationIcon: String
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
        val dateFormat = SimpleDateFormat("HH:mm a", getCurrentLocale())
        fragment_details_pick_up_time.text = dateFormat.format(Date(pickUpTime))
        fragment_details_drop_off_time.text = dateFormat.format(Date(dropOffTime))

    }

    private fun getCurrentLocale(): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context!!.resources.configuration.locales.get(0)
        } else {
            @Suppress("DEPRECATION")
            context!!.resources.configuration.locale
        }
    }

    override fun onDestroyView() {
        presenter.stop()
        super.onDestroyView()
    }
}