package com.example.mayank.nfc.roomdatabase.dao

import android.arch.persistence.room.*
import com.example.mayank.nfc.roomdatabase.NfcStudentAttendance

/**
 * Created by Mayank on 4/10/2018.
 */
@Dao
interface NfcStudentAttendanceDao {

    @Insert
    fun insert(attendance: NfcStudentAttendance)

    @Query("SELECT * FROM nfc_student_attendance")
    fun getAllAttendance(): List<NfcStudentAttendance>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(attendance: List<NfcStudentAttendance>?)

    @Update
    fun updateAttendance(attendance: NfcStudentAttendance)
}