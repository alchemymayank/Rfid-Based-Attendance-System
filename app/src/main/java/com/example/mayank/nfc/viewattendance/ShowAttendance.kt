package com.example.mayank.nfc.viewattendance

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.mayank.nfc.Constants.showLogDebug
import com.example.mayank.nfc.NfcApplication
import com.example.mayank.nfc.R
import com.example.mayank.nfc.roomdatabase.NfcStudentAttendance
import com.example.mayank.nfc.viewattendance.adapter.AttendanceAdapter

class ShowAttendance : AppCompatActivity() {

    private val TAG = ShowAttendance::class.java.simpleName

    lateinit var attendanceRecycler : RecyclerView
    val adapter: AttendanceAdapter by lazy { AttendanceAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_attendance)

        attendanceRecycler = findViewById<RecyclerView>(R.id.recycler_attendance_list) as RecyclerView
        attendanceRecycler.layoutManager = LinearLayoutManager(this)
        attendanceRecycler.setHasFixedSize(true)

        attendanceRecycler.adapter = adapter

        getAttandanceList()
    }

    private fun getAttandanceList() {
        showLogDebug(TAG, "Inside get attendance List")
        val list = NfcApplication.database.nfcStudentAttendanceDao().getAllAttendance()
        setRecyclerViewAdapter(list)
    }

    private fun setRecyclerViewAdapter(list: List<NfcStudentAttendance>) {
        adapter.items = list
    }
}
