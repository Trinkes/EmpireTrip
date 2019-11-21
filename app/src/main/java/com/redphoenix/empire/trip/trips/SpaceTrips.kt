package com.redphoenix.empire.trip.trips

data class SpaceTrips(val status: Status, val trips: List<SpaceTrip>) {

  enum class Status {
    OK, ERROR, NO_NETWORK
  }
}
