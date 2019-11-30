package com.sasuke.sms.util

import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

object DateTimeUtils {

    private val formatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)

    private val currentDate = ZonedDateTime.parse(ZonedDateTime.now().format(formatter))

    fun getDateFromMillis(millis: Long): String {
        return Instant.ofEpochMilli(millis)
            .atOffset(ZoneOffset.UTC)
            .atZoneSameInstant(ZoneId.systemDefault())
            .format(formatter)
    }

    fun getLocalDateFromString(date: String): ZonedDateTime {
        val localString = LocalDateTime.parse(date, formatter)
            .format(formatter)
        return ZonedDateTime.parse(localString)
    }

    fun getCurrentDate(): ZonedDateTime {
        return currentDate
    }

    fun getHoursAgoAccordingToCurrentTimeUsingMillis(millis: Long): Long {
        val smsData = getLocalDateFromString(getDateFromMillis(millis))
        return Duration.between(currentDate, smsData).toHours()

    }
}