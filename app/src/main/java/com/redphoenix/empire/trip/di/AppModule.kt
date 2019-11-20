package com.redphoenix.empire.trip.di

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val applicationContext: Context) {

    @Provides
    fun providesAppPackageName(): String {
        return applicationContext.packageName
    }
}