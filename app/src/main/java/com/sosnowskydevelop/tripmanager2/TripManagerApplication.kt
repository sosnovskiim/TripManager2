package com.sosnowskydevelop.tripmanager2

import android.app.Application
import com.sosnowskydevelop.tripmanager2.data.AppDatabase
import com.sosnowskydevelop.tripmanager2.data.RefuelingRepository
import com.sosnowskydevelop.tripmanager2.data.TripRepository

class TripManagerApplication : Application() {
    private val database by lazy { AppDatabase.getDatabase(context = this) }

    val tripRepository by lazy { TripRepository(tripDao = database.tripDao()) }
    val refuelingRepository by lazy { RefuelingRepository(refuelingDao = database.refuelingDao()) }
}