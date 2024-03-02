/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.custom

import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit

object Converter {
    fun splitYear(item: String): String {
        val date = item.split("-")
        return "(${date[0]})"
    }
    fun roundOffDecimal(number: Double): String {
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toString()
    }

    fun fromMinutesToHHmm(minutes: Int): String {
        val hours = TimeUnit.MINUTES.toHours(minutes.toLong())
        val remainMinutes = minutes - TimeUnit.HOURS.toMinutes(hours)
        return String.format("%dh %dm", hours, remainMinutes)
    }
}