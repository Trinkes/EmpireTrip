package com.redphoenix.empire.trip.details

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

class TripDetailsFragment : Fragment() {
    companion object {
        private const val TRIP_ID_KEY = "TRIP_ID"
        fun newInstance(tripId: Int): TripDetailsFragment {
            return TripDetailsFragment().apply {
                arguments = bundleOf(Pair(TRIP_ID_KEY, tripId))
            }
        }
    }
}