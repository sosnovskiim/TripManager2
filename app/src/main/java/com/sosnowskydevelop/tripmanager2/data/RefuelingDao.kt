package com.sosnowskydevelop.tripmanager2.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface RefuelingDao {
    @Query("SELECT * FROM refueling WHERE tripId = :tripId")
    fun getRefuelingList(tripId: Long): LiveData<List<Refueling>>

    @Query("SELECT * FROM refueling WHERE _id = :id")
    fun getRefueling(id: Long): LiveData<Refueling>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(refueling: Refueling)

    @Update
    suspend fun update(refueling: Refueling)

    @Delete
    suspend fun delete(refueling: Refueling)
}