package com.example.mayank.nfc

import android.util.Log
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Mayank on 4/7/2018.
 */
object Constants {

    val DISPLAY_DATE_FORMAT = "dd-MMM-yyyy hh:mm:ss aa"

    fun showLogDebug(tag : String, message : String){
        Log.d(tag, message)
    }

    fun showLogError(tag: String, message: String){
       Log.e(tag, message)
    }

    fun showLogWarning(tag: String, message : String){
        Log.w(tag, message)
    }

    fun getDateFormat(pattern: String, date: Date): String? {
        val formatter : DateFormat = SimpleDateFormat(pattern)
        return formatter.format(date)
    }

}