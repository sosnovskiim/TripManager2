package com.sosnowskydevelop.tripmanager2.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Trip")
data class Trip(
    val name: String,
    val beginDate: Date,
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long = 0
    var endDate: Date? = null
}