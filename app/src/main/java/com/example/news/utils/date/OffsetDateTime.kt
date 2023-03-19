package com.example.news.utils.date

import java.time.Duration
import java.time.OffsetDateTime

fun OffsetDateTime.getUploadedAgoText(): String {
    return try {
        val presentOffsetDateTime = OffsetDateTime.now()
        val duration = Duration.between(this, presentOffsetDateTime)

        val hourDifference = duration.toHours()
        val minuteDifference = duration.toMinutes()
        val dayDifference = duration.toDays()


        if (minuteDifference == 0L) {
            val secondDifference = duration.seconds.coerceAtLeast(0)
            "${secondDifference.addSuffixWithPluralization("second")} ago"
        } else if (hourDifference == 0L) {
            "${minuteDifference.addSuffixWithPluralization("minute")} ago"
        } else if (dayDifference == 0L) {
            "${hourDifference.addSuffixWithPluralization("hour")} ago"
        } else if (dayDifference <= 30) {
            "${dayDifference.addSuffixWithPluralization("day")} ago"
        } else if (dayDifference <= 365) {
            val month = dayDifference / 30
            "${month.addSuffixWithPluralization("month")} ago"
        } else {
            val year = dayDifference / 365
            "${year.addSuffixWithPluralization("year")} ago"
        }
    } catch (e: ArithmeticException) {
        "Posted long time ago"
    }
}


fun Long.addSuffixWithPluralization(suffix: String) = "$this $suffix${if (this > 1) "s" else ""}"

fun String.toOffsetDateTime(): OffsetDateTime = OffsetDateTime.parse(this)
