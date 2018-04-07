package com.example.mayank.nfc.nfcreader

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.*
import android.nfc.tech.Ndef
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.mayank.nfc.Constants.showLogDebug
import com.example.mayank.nfc.R
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.util.*
import kotlin.experimental.and

class NfcReaderActivity : AppCompatActivity() {

    var nfcTagTextView: TextView? = null
    internal lateinit var nfcAdapter: NfcAdapter
    private val MIME_TEXT_PLAIN = "text/plain"
    private lateinit var detectedTag: Tag
    private lateinit var tagId : String
    var inputTag : EditText? = null


    private val TAG = NfcReaderActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nfc_reader)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        nfcTagTextView = findViewById<TextView>(R.id.text_view_read_tag) as TextView
        inputTag = findViewById<EditText>(R.id.edit_text_nfc_tag) as EditText

        if (nfcAdapter!=null){
            Toast.makeText(this, "Nfc is Enable!", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Nfc is Disable", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onResume() {
        super.onResume()
        setupForegroundDispatch(this, nfcAdapter)
    }

    fun setupForegroundDispatch(activity: Activity, adapter: NfcAdapter?) {
        val intent = Intent(activity.applicationContext, activity.javaClass)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingIntent = PendingIntent.getActivity(activity.applicationContext, 0, intent, 0)

        val filters = arrayOfNulls<IntentFilter>(2)
        val techList = arrayOf<Array<String>>()

        // Notice that this is the same filter as in our manifest.
        filters[0] = IntentFilter()
        filters[0]!!.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED)
        filters[0]!!.addCategory(Intent.CATEGORY_DEFAULT)
        filters[1] = IntentFilter()
        filters[1]!!.addAction(NfcAdapter.ACTION_TECH_DISCOVERED)
        try {
            filters[0]!!.addDataType(MIME_TEXT_PLAIN)
        } catch (e: IntentFilter.MalformedMimeTypeException) {
            throw RuntimeException("Check your mime type.")
        }
        adapter?.enableForegroundDispatch(activity, pendingIntent, filters, techList)
    }

    override fun onNewIntent(intent: Intent) {
        ndefReader(intent)
    }

    private fun ndefReader(intent: Intent) {
        detectedTag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        val techList = detectedTag.techList
        val searchedTech = Ndef::class.java.name

        for (tech in techList) {
            if (searchedTech == tech) {
                NdefReaderTask().execute(detectedTag)
                break
            }
        }
    }

    private inner class NdefReaderTask : AsyncTask<Tag, Void, String>() {

        override fun doInBackground(vararg params: Tag): String? {
            val tag = params[0]
            val ndef = Ndef.get(tag)
                    ?: // NDEF is not supported by this Tag.
                    return null
            val ndefMessage = ndef.cachedNdefMessage ?: return null
            val records = ndefMessage.records
            for (ndefRecord in records) {
                if (ndefRecord.tnf == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.type, NdefRecord.RTD_TEXT)) {
                    try {
                        return readText(ndefRecord)
                    } catch (e: UnsupportedEncodingException) {
                        Log.e("LOG", "Unsupported Encoding", e)
                    }
                }
            }
            return null
        }

        @Throws(UnsupportedEncodingException::class)
        private fun readText(record: NdefRecord): String {
            /*
         * See NFC forum specification for "Text Record Type Definition" at 3.2.1
         *
         * http://www.nfc-forum.org/specs/
         *
         * bit_7 defines encoding
         * bit_6 reserved for future use, must be 0
         * bit_5..0 length of IANA language code
         */
            val payload = record.payload
            // Get the Text Encoding
            //val textEncoding = if (payload[0] and 128) == 0) "UTF-8" else "UTF-16"

            // Get the Language Code
            val languageCodeLength = payload[0] and 51

            // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
            // e.g. "en"

            // Get the Text
            return String(payload, languageCodeLength + 1, payload.size - languageCodeLength - 1, Charsets.UTF_8)
        }

        override fun onPostExecute(result: String?) {
            if (result != null) {
                tagId = result
                nfcTagTextView?.text = "NFC Id : $tagId"
                showLogDebug(TAG, "Content : $tagId")

            }
        }
    }


//    fun backToMainActivity(view: View){
//        showLogDebug(TAG, "Back to MainActivity Button clicked")
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
//        finish()
//    }

    fun writeNfcTag(view: View){
        showLogDebug(TAG, "Write Nfc Tag Button Clicked")
        val tag = inputTag?.text.toString().trim({ it <= ' ' })
        writeNfcTag(tag)

    }

    private fun writeNfcTag(text: String) {
        val intent = intent
        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        val ndef = Ndef.get(tag)
        if (ndef != null) {
            try {
                ndef.connect()
                val mimeRecord = createRecord(text)//NdefRecord.createMime("text/plain", text.getBytes(Charset.forName("US-ASCII")));
                ndef.writeNdefMessage(NdefMessage(mimeRecord))
                ndef.close()
                showLogDebug(TAG, "Write Nfc Id successfully")
            } catch (e: IOException) {
                showLogDebug(TAG, "IOException $e")
                Toast.makeText(this, "Attach Id card and try again!", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            } catch (e: FormatException) {
                showLogDebug(TAG, "Format Exception $e")
                e.printStackTrace()
            }

        } else {
            showLogDebug(TAG, "Ndef is null")
        }
    }

    @Throws(UnsupportedEncodingException::class)
    private fun createRecord(text: String): NdefRecord {
        val lang = "en"
        val textBytes = text.toByteArray()
        val langBytes = lang.toByteArray(charset("US-ASCII"))
        val langLength = langBytes.size
        val textLength = textBytes.size
        val payload = ByteArray(1 + langLength + textLength)

        // set status byte (see NDEF spec for actual bits)
        payload[0] = langLength.toByte()

        // copy langbytes and textbytes into payload
        System.arraycopy(langBytes, 0, payload, 1, langLength)
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength)

        return NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, ByteArray(0), payload)
    }

}
