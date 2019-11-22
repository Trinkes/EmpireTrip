package com.redphoenix.empire.trip

import android.app.Application
import com.redphoenix.empire.trip.di.AppComponent
import com.redphoenix.empire.trip.di.AppModule
import com.redphoenix.empire.trip.di.DaggerAppComponent
import javax.inject.Inject

open class EmpireTripApplication : Application() {
  lateinit var appComponent: AppComponent
  @Inject
  lateinit var appPackageName: String

  override fun onCreate() {
    super.onCreate()
    appComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

    appComponent.inject(this)
  }
}