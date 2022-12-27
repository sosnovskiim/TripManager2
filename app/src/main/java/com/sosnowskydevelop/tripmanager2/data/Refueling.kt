package com.sosnowskydevelop.tripmanager2.data

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.sosnowskydevelop.tripmanager2.NOT_TO_FULL_CAPITALIZE
import com.sosnowskydevelop.tripmanager2.NOT_TO_FULL_LOWER
import com.sosnowskydevelop.tripmanager2.TO_FULL_CAPITALIZE
import com.sosnowskydevelop.tripmanager2.TO_FULL_LOWER

@Entity(tableName = "Refueling")
data class Refueling(
    val tripId: Long,
    val odometer: String,
    val litersFilled: String,
    val pricePerLiter: String,
    val isToFull: Boolean,
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Long = 0

    @get: Ignore
    val isToFullCapitalize: String
        get() {
            return if (isToFull) {
                TO_FULL_CAPITALIZE
            } else {
                NOT_TO_FULL_CAPITALIZE
            }
        }

    @get: Ignore
    val isToFullLower: String
        get() {
            return if (isToFull) {
                TO_FULL_LOWER
            } else {
                NOT_TO_FULL_LOWER
            }
        }

    constructor(parcel: Parcel) : this(
        tripId = parcel.readLong(),
        odometer = parcel.readString().toString(),
        litersFilled = parcel.readString().toString(),
        pricePerLiter = parcel.readString().toString(),
        isToFull = parcel.readByte() != 0.toByte(),
    ) {
        id = parcel.readLong()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(odometer)
        p0?.writeString(litersFilled)
        p0?.writeString(pricePerLiter)
        p0?.writeBoolean(isToFull)
        p0?.writeLong(tripId)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Refueling> {
        override fun createFromParcel(p0: Parcel?): Refueling? = p0?.let { Refueling(it) }

        override fun newArray(p0: Int): Array<Refueling?> = arrayOfNulls(p0)
    }
}