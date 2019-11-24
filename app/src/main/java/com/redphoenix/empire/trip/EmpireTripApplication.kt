package com.redphoenix.empire.trip

import android.app.Application
import com.redphoenix.empire.trip.di.AppComponent
import com.redphoenix.empire.trip.di.AppModule
import com.redphoenix.empire.trip.di.DaggerAppComponent

open class EmpireTripApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = createAppComponent()

        appComponent.inject(this)
    }

    open fun createAppComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}