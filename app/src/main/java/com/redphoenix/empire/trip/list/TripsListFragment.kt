package com.redphoenix.empire.trip.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.redphoenix.empire.trip.EmpireTripApplication
import com.redphoenix.empire.trip.R
import com.redphoenix.empire.trip.trips.Trips
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class TripsListFragment : Fragment(), TripsListView {
    companion object {
        private val TAG = TripsListFragment::class.java.simpleName
        fun newInstance(): TripsListFragment {
            return TripsListFragment()
        }
    }

    @Inject
    lateinit var trips: Trips
    @Inject
    lateinit var mapper: TripsViewMapper

    private lateinit var presenter: TripsListPresenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as EmpireTripApplication).appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = TripsListPresenter(this, trips, AndroidSchedulers.mainThread(), mapper)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.trips_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.present()
    }

    override fun showTrips(trips: List<TripsListView.TripViewEntity>) {
        Log.d(TAG, "showTrips() called with: trips = [$trips]")
    }

    override fun showGenericError() {
        Log.d(TAG, "showGenericError() called")
    }

    override fun showNoNetworkError() {
        Log.d(TAG, "showNoNetworkError() called")
    }
}