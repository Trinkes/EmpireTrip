package com.redphoenix.empire.trip.list

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.view.RxView
import com.redphoenix.empire.trip.EmpireTripApplication
import com.redphoenix.empire.trip.R
import com.redphoenix.empire.trip.TripsListNavigator
import com.redphoenix.empire.trip.components.getService
import com.redphoenix.empire.trip.trips.Trips
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.generic_error_layout.*
import kotlinx.android.synthetic.main.network_error_layout.*
import kotlinx.android.synthetic.main.trips_list_fragment.*
import javax.inject.Inject

class TripsListFragment : Fragment(), TripsListView {
    companion object {
        private val TAG = TripsListFragment::class.java.simpleName
        private const val STATE_KEY = "STATE_KEY"
        fun newInstance(): TripsListFragment {
            return TripsListFragment()
        }
    }

    @Inject
    lateinit var trips: Trips
    @Inject
    lateinit var mapper: TripsViewMapper
    @Inject
    lateinit var differ: TripViewEntityDiffer

    private var adapter: ListTripsAdapter? = null
    private lateinit var presenter: TripsListPresenter
    private var viewRestoreState: Parcelable? = null
    private var tripClicks: PublishSubject<Int> =
        PublishSubject.create()
    private lateinit var navigator: TripsListNavigator
    private lateinit var layoutManager: LinearLayoutManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as EmpireTripApplication).appComponent.inject(this)
        navigator = getService()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = TripsListPresenter(this, trips, AndroidSchedulers.mainThread(), mapper)
        adapter = ListTripsAdapter(differ, layoutInflater, tripClicks)
    }

    override fun getTripClicks(): Observable<Int> {
        return tripClicks
    }

    override fun showTripDetails(tripId: Int) {
        navigator.showTripDetails(tripId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.trips_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(view.context)
        list.layoutManager = layoutManager
        list.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(
                view.context,
                R.drawable.center
            )!!
        )
        list.addItemDecoration(dividerItemDecoration)
        presenter.present(savedInstanceState == null)
    }

    override fun restoreTrips(trips: List<TripsListView.TripViewEntity>) {
        adapter!!.setTrips(trips)
        showView(ViewsGroup.LIST_VIEW)
        layoutManager.onRestoreInstanceState(viewRestoreState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(STATE_KEY, layoutManager.onSaveInstanceState())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            viewRestoreState = savedInstanceState.getParcelable(STATE_KEY)
        }
    }


    override fun onDestroyView() {
        presenter.stop()
        super.onDestroyView()
    }

    override fun onDestroy() {
        adapter = null
        super.onDestroy()
    }

    override fun showTrips(trips: List<TripsListView.TripViewEntity>) {
        showView(ViewsGroup.LIST_VIEW)
        adapter!!.setTrips(trips)
    }

    override fun getRetryClicks(): Observable<Any> {
        return Observable.merge(
            RxView.clicks(no_network_retry_button),
            RxView.clicks(generic_error_retry_button)
        )
    }

    override fun showGenericError() {
        showView(ViewsGroup.GENERIC_ERROR_VIEW)
    }

    override fun showNoNetworkError() {
        showView(ViewsGroup.NETWORK_ERROR_VIEW)
    }

    private fun showView(view: ViewsGroup) {
        when (view) {
            ViewsGroup.LIST_VIEW -> {
                list.visibility = View.VISIBLE
                generic_error_layout.visibility = View.GONE
                network_error_layout.visibility = View.GONE
            }

            ViewsGroup.GENERIC_ERROR_VIEW -> {
                list.visibility = View.GONE
                generic_error_layout.visibility = View.VISIBLE
                network_error_layout.visibility = View.GONE
            }
            ViewsGroup.NETWORK_ERROR_VIEW -> {
                list.visibility = View.GONE
                generic_error_layout.visibility = View.GONE
                network_error_layout.visibility = View.VISIBLE
            }
        }
    }

    enum class ViewsGroup {
        LIST_VIEW, GENERIC_ERROR_VIEW, NETWORK_ERROR_VIEW
    }
}