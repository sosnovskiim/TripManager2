package com.sosnowskydevelop.tripmanager2.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sosnowskydevelop.tripmanager2.data.Trip
import com.sosnowskydevelop.tripmanager2.data.TripRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TripViewModel(private val tripRepository: TripRepository) : ViewModel() {
    val tripList: LiveData<List<Trip>> =
        tripRepository.tripList

    fun getTrip(id: Long): LiveData<Trip> =
        tripRepository.getTrip(id = id)

    fun insert(trip: Trip) = viewModelScope.launch {
        tripRepository.insert(trip = trip)
    }

    fun update(trip: Trip) = viewModelScope.launch {
        tripRepository.update(trip = trip)
    }

    fun delete(trip: Trip) = viewModelScope.launch {
        tripRepository.delete(trip = trip)
    }
}

class TripViewModelFactory(private val repository: TripRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TripViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TripViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}