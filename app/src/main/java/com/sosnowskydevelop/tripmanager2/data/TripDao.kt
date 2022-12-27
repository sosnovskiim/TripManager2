package com.sosnowskydevelop.tripmanager2.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TripDao {
    @Query("SELECT * FROM trip ORDER BY _id DESC")
    fun getTripList(): LiveData<List<Trip>>

    @Query("SELECT * FROM trip WHERE _id = :id")
    fun getTrip(id: Long): LiveData<Trip>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(trip: Trip)

    @Update
    suspend fun update(trip: Trip)

    @Delete
    suspend fun delete(trip: Trip)
}