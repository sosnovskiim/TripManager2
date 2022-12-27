package com.sosnowskydevelop.tripmanager2.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class TripRepository(private val tripDao: TripDao) {
    val tripList: LiveData<List<Trip>> = tripDao.getTripList()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getTrip(id: Long): LiveData<Trip> {
        return tripDao.getTrip(id = id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(trip: Trip) {
        tripDao.insert(trip = trip)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(trip: Trip) {
        tripDao.update(trip = trip)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(trip: Trip) {
        tripDao.delete(trip = trip)
    }
}