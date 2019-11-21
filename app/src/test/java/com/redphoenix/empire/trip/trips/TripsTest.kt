package com.redphoenix.empire.trip.trips

import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.BehaviorSubject
import org.junit.Before
import org.junit.Test

internal class TripsTest {
  private lateinit var trips: Trips
  private lateinit var scheduler: TestScheduler
  private val tripListTestRelay = BehaviorSubject.create<SpaceTripsResponse>()
  private val tripTestRelay = BehaviorSubject.create<SpaceTripResponse>()

  @Before
  fun setUp() {
    scheduler = TestScheduler()
    val repository = object : TripsRepository {
      override fun getSpaceTrips(): Observable<SpaceTripsResponse> {
        return tripListTestRelay
      }

      override fun getSpaceTrip(tripId: Int): Observable<SpaceTripResponse> {
        return tripTestRelay
      }
    }
    trips = Trips(scheduler, repository)
  }

  @Test
  fun getSpaceTravelsList_OkResponse() {
    val tripsTest = trips.getSpaceTrips()
        .test()

    val response = SpaceTripsResponse(ResponseStatus.OK, listOf(SpaceTrip(1)))
    tripListTestRelay.onNext(response)
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

    val response = SpaceTripsResponse(ResponseStatus.ERROR, emptyList())
    tripListTestRelay.onNext(response)
    scheduler.triggerActions()

    tripsTest.assertValueCount(1)
        .assertValues(response)
        .assertNoErrors()
        .assertNotComplete()

  }

  @Test
  fun getSpaceTrip_OkResponse() {
    val tripTest = trips.getSpaceTrip(1)
        .test()

    val spaceTripResponse = SpaceTripResponse(ResponseStatus.OK, SpaceTrip(1))
    tripTestRelay.onNext(spaceTripResponse)
    scheduler.triggerActions()

    tripTest.assertValueCount(1)
        .assertValue(spaceTripResponse)
        .assertNoErrors()
        .assertNotComplete()
  }

  @Test
  fun getSpaceTrip_ErrorResponse() {
    val tripTest = trips.getSpaceTrip(1)
        .test()

    val spaceTripResponse = SpaceTripResponse(ResponseStatus.ERROR, null)
    tripTestRelay.onNext(spaceTripResponse)
    scheduler.triggerActions()

    tripTest.assertValueCount(1)
        .assertValue(spaceTripResponse)
        .assertNoErrors()
        .assertNotComplete()
  }

}