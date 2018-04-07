package com.example.mayank.nfc.createnfc

import android.nfc.*
import android.nfc.tech.Ndef
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.mayank.nfc.Constants.showLogDebug
import com.example.mayank.nfc.R
import java.io.IOException
import java.io.UnsupportedEncodingException

class CreateNfcActivity : AppCompatActivity() {

    private val TAG = CreateNfcActivity::class.java.simpleName
    var inputTag : EditText? = null
    internal lateinit var nfcAdapter: NfcAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_nfc)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        inputTag = findViewById<EditText>(R.id.edit_text_nfc_tag) as EditText
    }




}
