package com.example.mayank.nfc.roomdatabase

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.example.mayank.nfc.roomdatabase.dao.NfcStudentAttendanceDao

/**
 * Created by Mayank on 4/10/2018.
 */
@Database(entities = [(NfcStudentAttendance::class)], version = 1)
@TypeConverters(Converters::class)
abstract class NfcDatabase : RoomDatabase() {

    abstract fun nfcStudentAttendanceDao() : NfcStudentAttendanceDao
}