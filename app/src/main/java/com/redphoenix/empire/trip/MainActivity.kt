package com.redphoenix.empire.trip

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.redphoenix.empire.trip.list.TripsListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TripsListFragment.newInstance())
                .commit()
        }
    }
}