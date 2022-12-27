package com.sosnowskydevelop.tripmanager2.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class RefuelingRepository(private val refuelingDao: RefuelingDao) {
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getRefuelingList(tripId: Long): LiveData<List<Refueling>> {
        return refuelingDao.getRefuelingList(tripId = tripId)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun getRefueling(id: Long): LiveData<Refueling> {
        return refuelingDao.getRefueling(id = id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(refueling: Refueling) {
        refuelingDao.insert(refueling = refueling)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(refueling: Refueling) {
        refuelingDao.update(refueling = refueling)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(refueling: Refueling) {
        refuelingDao.delete(refueling = refueling)
    }
}