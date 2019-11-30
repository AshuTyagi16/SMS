package com.sasuke.sms.data

class DataItem(val sms: SMS) : ListItem() {

    override fun getType(): Int {
        return TYPE_DATA
    }

    override fun getId(): Long {
        return sms.id.toLong()
    }
}