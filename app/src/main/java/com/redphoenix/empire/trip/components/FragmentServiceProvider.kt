package com.redphoenix.empire.trip.components

import androidx.fragment.app.Fragment

inline fun <reified T> Fragment.getService(): T {
    val ctx = context
    if (ctx != null && ctx is T) {
        return ctx
    } else {
        throw IllegalArgumentException("This fragment need to be attached to ${T::class.java.simpleName}")
    }
}