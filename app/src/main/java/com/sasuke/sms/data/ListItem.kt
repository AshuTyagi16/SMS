package com.sasuke.sms.data

abstract class ListItem {
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_DATA = 1
    }

    abstract fun getType(): Int
}