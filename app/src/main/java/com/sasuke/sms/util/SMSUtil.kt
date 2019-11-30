package com.sasuke.sms.util

import android.content.ContentResolver
import android.provider.Telephony
import androidx.annotation.WorkerThread
import com.sasuke.sms.data.SMS
import kotlin.collections.ArrayList

class SMSUtil(private val contentResolver: ContentResolver) {

    private lateinit var sms: SMS

    @WorkerThread
    suspend fun getAllSmsFromProvider(): List<SMS> {
        val lstSms: MutableList<SMS> = ArrayList()
        val cr: ContentResolver = contentResolver
        cr.query(
            Telephony.Sms.Inbox.CONTENT_URI,
            arrayOf(
                Telephony.Sms._ID,
                Telephony.Sms.ADDRESS,
                Telephony.Sms.BODY,
                Telephony.Sms.READ,
                Telephony.Sms.DATE,
                Telephony.Sms.TYPE
            ),
            null,
            null,
            Telephony.Sms.Inbox.DEFAULT_SORT_ORDER
        )?.let { cursor ->
            val totalSMS: Int = cursor.count
            if (cursor.moveToFirst()) {
                for (i in 0 until totalSMS) {
                    val time = cursor.getString(cursor.getColumnIndexOrThrow("date"))
                    sms = SMS(
                        id = cursor.getString(cursor.getColumnIndexOrThrow("_id")),
                        address = cursor.getString(cursor.getColumnIndexOrThrow("address")),
                        message = cursor.getString(cursor.getColumnIndexOrThrow("body")),
                        readState = cursor.getString(cursor.getColumnIndex("read")),
                        time = time,
                        folderName = if (cursor.getString(cursor.getColumnIndexOrThrow("type")).contains(
                                Constants.FLAG_TYPE_INBOX
                            )
                        ) {
                            Constants.FOLDER_TYPE_INBOX
                        } else {
                            Constants.FOLDER_TYPE_SENT
                        },
                        hoursAgo = DateTimeUtils.getHoursAgoAccordingToCurrentTimeUsingMillis(time.toLong())
                    )
                    lstSms.add(sms)
                    cursor.moveToNext()
                }
            }
            cursor.close()
        } ?: run {

        }
        return lstSms
    }

}