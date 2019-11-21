package com.redphoenix.empire.trip.trips

import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.BehaviorSubject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class TripsTest {
  private lateinit var trips: Trips
  private lateinit var scheduler: TestScheduler
  private val relay = BehaviorSubject.create<SpaceTrips>()

  @Before
  fun setUp() {
    scheduler = TestScheduler()
    val repository = object : TripsRepository {
      override fun getSpaceTrips(): Observable<SpaceTrips> {
        return relay
      }
    }
    trips = Trips(scheduler, repository)
  }

  @Test
  fun getSpaceTravelsList_OkResponse() {
    val tripsTest = trips.getSpaceTrips()
        .test()

    val response = SpaceTrips(SpaceTrips.Status.OK, listOf(SpaceTrip(1)))
    relay.onNext(response)
    scheduler.triggerActions()

    tripsTest.assertValueCount(1)
        .assertValues(response)
        .assertNoErrors()
        .assertNotComplete()
  }

  @Test
  fun getSpaceTravelsList_ErrorResponse() {
    val tripsTest = trips.getSpaceTrips()
        .test()

    val response = SpaceTrips(SpaceTrips.Status.ERROR, emptyList())
    relay.onNext(response)
    scheduler.triggerActions()

    tripsTest.assertValueCount(1)
        .assertValues(response)
        .assertNoErrors()
        .assertNotComplete()

  }


}