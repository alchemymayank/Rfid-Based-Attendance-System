package com.example.mayank.nfc

import android.app.Application
import android.arch.persistence.room.Room
import com.example.mayank.nfc.roomdatabase.NfcDatabase

/**
 * Created by Mayank on 4/10/2018.
 */
class NfcApplication : Application() {

    companion object {
        lateinit var database: NfcDatabase

    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, NfcDatabase::class.java, "nfc_database")
                .allowMainThreadQueries().build()
    }
}