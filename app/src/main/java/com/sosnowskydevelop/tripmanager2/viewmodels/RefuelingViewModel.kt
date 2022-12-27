package com.sosnowskydevelop.tripmanager2.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.sosnowskydevelop.tripmanager2.data.Refueling
import com.sosnowskydevelop.tripmanager2.data.RefuelingRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class RefuelingViewModel(private val refuelingRepository: RefuelingRepository) : ViewModel() {
    fun getRefuelingList(tripId: Long): LiveData<List<Refueling>> =
        refuelingRepository.getRefuelingList(tripId = tripId)

    fun getRefueling(id: Long): LiveData<Refueling> =
        refuelingRepository.getRefueling(id = id)

    fun insert(refueling: Refueling) = viewModelScope.launch {
        refuelingRepository.insert(refueling = refueling)
    }

    fun update(refueling: Refueling) = viewModelScope.launch {
        refuelingRepository.update(refueling = refueling)
    }

    fun delete(refueling: Refueling) = viewModelScope.launch {
        refuelingRepository.delete(refueling = refueling)
    }

    fun getAverageFuelConsumption(refuelingList: List<Refueling>): String {
        when {
            refuelingList.isEmpty() -> {
                return "Заправок нет"
            }
            refuelingList.size == 1 -> {
                return "Недостаточно данных"
            }
            else -> {
                val refuelingListForCalculatingDistance: List<Refueling> =
                    refuelingList.dropWhile { !it.isToFull }.dropLastWhile { !it.isToFull }
                if (refuelingListForCalculatingDistance.size <= 1) {
                    return "Недостаточно данных"
                } else {
                    val distanceForCalculating =
                        refuelingListForCalculatingDistance[
                                refuelingListForCalculatingDistance.size - 1
                        ].odometer.toInt() - refuelingListForCalculatingDistance[0].odometer.toInt()
                    val refuelingListForCalculatingLiters: List<Refueling> =
                        refuelingListForCalculatingDistance.drop(1)
                    val litersForCalculating: Double =
                        if (refuelingListForCalculatingLiters.size == 1) {
                            refuelingListForCalculatingLiters[0].litersFilled.toDouble()
                        } else {
                            refuelingListForCalculatingLiters.sumOf { it.litersFilled.toDouble() }
                        }

                    return String.format(
                        "%.2f",
                        litersForCalculating / distanceForCalculating * 100
                    )
                }
            }
        }
    }
}

class RefuelingViewModelFactory(private val repository: RefuelingRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RefuelingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RefuelingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}