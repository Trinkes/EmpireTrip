package com.redphoenix.empire.trip.di

import com.redphoenix.empire.trip.EmpireTripApplication
import com.redphoenix.empire.trip.details.TripDetailsFragment
import com.redphoenix.empire.trip.list.TripsListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(context: EmpireTripApplication)
    fun inject(fragment: TripsListFragment)
    fun inject(tripDetailsFragment: TripDetailsFragment)
}