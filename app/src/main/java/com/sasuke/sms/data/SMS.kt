package com.sasuke.sms.data

data class SMS(
    val id: String,
    val address: String,
    val message: String,
    val readState: String,
    val time: String,
    val folderName: String,
    val hoursAgo: Long
) {
}