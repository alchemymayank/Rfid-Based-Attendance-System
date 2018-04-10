package com.example.mayank.nfc.viewattendance.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.example.mayank.nfc.Constants.DISPLAY_DATE_FORMAT
import com.example.mayank.nfc.Constants.getDateFormat
import com.example.mayank.nfc.R
import com.example.mayank.nfc.roomdatabase.Converters
import com.example.mayank.nfc.roomdatabase.NfcStudentAttendance
import kotlinx.android.synthetic.main.subject_item.view.*
import org.w3c.dom.Text
import java.util.*

/**
 * Created by Mayank on 4/10/2018.
 */
class AttendanceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(attendanceViewModel : NfcStudentAttendance){
        val textViewStudentId = itemView.findViewById<TextView>(R.id.text_view_student_id) as TextView
        val textViewSubject = itemView.findViewById<TextView>(R.id.text_view_subject) as TextView
        val textViewFacultyName = itemView.findViewById<TextView>(R.id.text_view_faculty_name) as TextView
        val textViewTrackTime = itemView.findViewById<TextView>(R.id.text_view_track_time) as TextView

        textViewStudentId.text = attendanceViewModel.nfcStudentId
        textViewSubject.text = attendanceViewModel.subject
        textViewFacultyName.text = attendanceViewModel.facultyName
        val date : Date? = Converters.fromTimestamp(attendanceViewModel.trackTime)
        val time =  getDateFormat(DISPLAY_DATE_FORMAT, date!!)
        textViewTrackTime.text = time
    }
}