package com.example.mayank.nfc.roomdatabase

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Mayank on 4/10/2018.
 */
@Entity(tableName = "nfc_student_attendance")
class NfcStudentAttendance {

    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0

    @ColumnInfo(name = "nfc_student_id")
    var nfcStudentId: String? = null

    @ColumnInfo(name = "subject")
    var subject: String? = null

    @ColumnInfo(name = "faculty_name")
    var facultyName: String? = null

    @ColumnInfo(name = "track_time")
    var trackTime: Long? = null

    @ColumnInfo(name = "sync_state")
    var syncState: Byte? = null
}