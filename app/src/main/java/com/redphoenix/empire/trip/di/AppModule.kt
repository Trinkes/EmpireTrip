package com.redphoenix.empire.trip.di

import com.google.gson.Gson
import com.redphoenix.empire.trip.BuildConfig
import com.redphoenix.empire.trip.list.TripViewEntityDiffer
import com.redphoenix.empire.trip.list.TripsViewMapper
import com.redphoenix.empire.trip.network.AcceptedDataTypeInterceptor
import com.redphoenix.empire.trip.network.LogInterceptor
import com.redphoenix.empire.trip.trips.ApiTripsResponseMapper
import com.redphoenix.empire.trip.trips.NetworkTripsRepository
import com.redphoenix.empire.trip.trips.Trips
import com.redphoenix.empire.trip.trips.TripsApi
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providesTrips(tripsApi: TripsApi): Trips {
        return Trips(Schedulers.io(), NetworkTripsRepository(tripsApi, ApiTripsResponseMapper()))
    }

    @Provides
    @Singleton
    fun providesTripsViewMapper(): TripsViewMapper {
        return TripsViewMapper()
    }

    @Provides
    @Singleton
    @Named("isDebug")
    fun providesIsDebug(): Boolean {
        return BuildConfig.DEBUG
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(@Named("isDebug") isDebug: Boolean): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (isDebug) {
            builder.addInterceptor(LogInterceptor())
        }
        builder.addInterceptor(AcceptedDataTypeInterceptor())

        return builder.build()
    }

    @Provides
    @Singleton
    fun providesTripsApi(client: OkHttpClient): TripsApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_HOST)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(TripsApi::class.java)
    }

    @Provides
    @Singleton
    fun providesTripViewEntityDiffer(): TripViewEntityDiffer {
        return TripViewEntityDiffer()
    }

}