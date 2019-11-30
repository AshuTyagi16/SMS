package com.sasuke.sms.util

import android.content.ContentResolver
import android.provider.Telephony
import androidx.annotation.WorkerThread
import com.sasuke.sms.data.DataItem
import com.sasuke.sms.data.HeaderItem
import com.sasuke.sms.data.ListItem
import com.sasuke.sms.data.SMS
import kotlin.collections.ArrayList

class SMSUtil(private val contentResolver: ContentResolver) {

    private var totalSMSCount = 0
    private lateinit var sms: SMS
    private lateinit var consolidatedList: ArrayList<ListItem>

    private suspend fun getAllSmsFromProvider(): List<SMS> {
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
            totalSMSCount = cursor.count
            if (cursor.moveToFirst()) {
                for (i in 0 until totalSMSCount) {
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
        }
        return lstSms
    }

    private suspend fun getGroupDataIntoHashMap(): HashMap<Long, ArrayList<SMS>> {
        val map: HashMap<Long, ArrayList<SMS>> = HashMap()
        getAllSmsFromProvider().forEach {
            val hoursAgo = it.hoursAgo
            if (map.containsKey(hoursAgo)) {
                map[hoursAgo]?.add(it)
            } else {
                val list = ArrayList<SMS>()
                list.add(it)
                map[hoursAgo] = list
            }
        }
        return map
    }

    @WorkerThread
    suspend fun getConsolidatedList(): ArrayList<ListItem> {
        consolidatedList = ArrayList(totalSMSCount)
        getGroupDataIntoHashMap().let { map ->
            map.keys.forEach {
                val headerItem = HeaderItem(it)
                consolidatedList.add(headerItem)
                map[it]?.forEach {
                    val dataItem = DataItem(it)
                    consolidatedList.add(dataItem)
                }
            }
        }
        return consolidatedList
    }

}