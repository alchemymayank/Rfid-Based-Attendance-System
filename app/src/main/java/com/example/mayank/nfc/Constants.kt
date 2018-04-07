package com.example.mayank.nfc

import android.util.Log

/**
 * Created by Mayank on 4/7/2018.
 */
object Constants {

    fun showLogDebug(tag : String, message : String){
        Log.d(tag, message)
    }

    fun showLogError(tag: String, message: String){
       Log.e(tag, message)
    }

    fun showLogWarning(tag: String, message : String){
        Log.w(tag, message)
    }
}