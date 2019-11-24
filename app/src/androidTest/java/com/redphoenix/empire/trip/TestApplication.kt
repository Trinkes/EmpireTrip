package com.redphoenix.empire.trip

import android.app.Application
import com.redphoenix.empire.trip.di.AppComponent
import com.redphoenix.empire.trip.di.AppModule
import com.redphoenix.empire.trip.di.DaggerAppComponent
import com.redphoenix.empire.trip.trips.*
import dagger.Module
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler

class TestApplication : EmpireTripApplication() {
    val testScheduler = TestScheduler()

    override fun createAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .appModule(MockApplicationModule(this))
            .build()

    }

    @Module
    inner class MockApplicationModule internal constructor(application: Application) :
        AppModule(application) {
        override fun providesTrips(tripsApi: TripsApi): Trips {
            return Trips(testScheduler, object : TripsRepository {
                override fun getSpaceTrips(): Observable<SpaceTripsResponse> {
                    return Observable.just(
                        SpaceTripsResponse(ResponseStatus.OK, listOf(spaceTrip))
                    )
                }

                override fun getSpaceTrip(tripId: Int): Observable<SpaceTripResponse> {
                    return Observable.just(
                        SpaceTripResponse(ResponseStatus.OK, spaceTrip)
                    )
                }
            })
        }
    }
}