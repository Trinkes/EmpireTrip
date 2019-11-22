package com.redphoenix.empire.trip.tools

import okhttp3.Interceptor
import okhttp3.Response

class AcceptedDataTypeInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    return chain.proceed(
        chain.request()
            .newBuilder()
            .header("Accept", "application/json")
            .build())
  }
}
