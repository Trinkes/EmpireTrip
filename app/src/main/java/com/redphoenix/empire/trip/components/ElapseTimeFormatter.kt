package com.redphoenix.empire.trip.components

import android.text.format.DateUtils
import android.util.Log
import java.text.SimpleDateFormat

class ElapseTimeFormatter(private val formatter: SimpleDateFormat) {
    companion object {
        private val TAG = ElapseTimeFormatter::class.java.simpleName
    }

    /**
     * This formatter only works correctly for durations lower than 24h, after that, it will
     * increase 1 hour per day
     */
    fun format(duration: Long): String {
        if (duration >= DateUtils.DAY_IN_MILLIS) {
            Log.w(
                TAG, "format: ${ElapseTimeFormatter::class.java} only works for durations " +
                        "lower that 24h\nDuration: $duration"
            )
        }
        return formatter.format(duration - DateUtils.HOUR_IN_MILLIS)
    }
}
