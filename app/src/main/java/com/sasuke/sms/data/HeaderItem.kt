package com.sasuke.sms.data

class HeaderItem(val hoursAgo: Long) : ListItem() {

    override fun getType(): Int {
        return TYPE_HEADER
    }
}