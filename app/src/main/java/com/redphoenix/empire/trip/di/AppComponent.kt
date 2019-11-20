package com.redphoenix.empire.trip.di

import com.redphoenix.empire.trip.EmpireTripApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(context: EmpireTripApplication)
}