package com.example.mayank.nfc.roomdatabase

import android.arch.persistence.room.TypeConverter
import java.util.*

/**
 * Created by Mayank on 4/10/2018.
 */
object Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): Long? {
        return (if (date == null) null else date.time)?.toLong()

    }
}