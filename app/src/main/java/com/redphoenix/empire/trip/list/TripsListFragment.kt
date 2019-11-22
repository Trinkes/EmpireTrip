package com.redphoenix.empire.trip.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.redphoenix.empire.trip.R

class TripsListFragment : Fragment() {
  companion object {
    fun newInstance(): TripsListFragment {
      return TripsListFragment()
    }
  }

  private lateinit var presenter: TripsListPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    presenter = TripsListPresenter()
  }

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.trips_list_fragment, container, false)
  }
}