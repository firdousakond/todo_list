package com.tech.todolist.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtils internal constructor() {
    companion object {
        private const val DATE_MONTH_TIME_FORMAT = "dd MMM yyyy hh:mm aa"
        private fun toDateFromUnixTimeStamp(unixTime: Long): Date {
            return Date(unixTime)
        }


        fun getDateTimeMonthFromMillis(millis: Long): String {
            val dateGiven =
                toDateFromUnixTimeStamp(millis)
            val formatter = SimpleDateFormat(
                DATE_MONTH_TIME_FORMAT,
                Locale.ENGLISH
            )
            return formatter.format(dateGiven)
        }

    }

    init {
        Logger.debug("Private constructor")
    }
}