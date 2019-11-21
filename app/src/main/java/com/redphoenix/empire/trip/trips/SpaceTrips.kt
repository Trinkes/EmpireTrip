package com.redphoenix.empire.trip.trips

data class SpaceTrips(val status: Status, val travelers: List<SpaceTraveler>) {

  enum class Status {
    OK, ERROR, NO_NETWORK
  }
}
