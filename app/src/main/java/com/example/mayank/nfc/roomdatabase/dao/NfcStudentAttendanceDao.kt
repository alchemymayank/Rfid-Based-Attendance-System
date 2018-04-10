package com.example.mayank.nfc.roomdatabase.dao

import android.arch.persistence.room.*
import com.example.mayank.nfc.roomdatabase.NfcStudentAttendance

/**
 * Created by Mayank on 4/10/2018.
 */
@Dao
interface NfcStudentAttendanceDao {

    @Insert
    fun insert(location: NfcStudentAttendance)

    @Query("SELECT * FROM nfc_student_attendance")
    fun getAllLocation(): List<NfcStudentAttendance>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(location: List<NfcStudentAttendance>?)

    @Update
    fun updateLocation(location: NfcStudentAttendance)
}