package com.example.mayank.nfc.viewattendance.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.mayank.nfc.R
import com.example.mayank.nfc.roomdatabase.NfcStudentAttendance

/**
 * Created by Mayank on 4/10/2018.
 */
class AttendanceAdapter: RecyclerView.Adapter<AttendanceViewHolder>() {

    lateinit var  context : Context
    var items: List<NfcStudentAttendance> = emptyList()
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AttendanceViewHolder {
        context = parent!!.context
        val v = LayoutInflater.from(context).inflate(R.layout.subject_item, parent, false)
        return AttendanceViewHolder(v)
    }

    override fun onBindViewHolder(holder: AttendanceViewHolder?, position: Int) {
        holder!!.bindView(items[position])
    }
}