package com.dibyendu.picker.util

import android.content.Context
import com.dibyendu.picker.R
import com.dibyendu.picker.model.Item
import java.text.DateFormatSymbols
import java.util.*

object PickerUtils {
    internal const val RESULT_TAG = "Results"
    internal const val MONTH_TAG = "Month"
    internal const val YEAR_TAG = "Year"

    enum class Format {
        SHORT,
        LONG
    }

    private fun getMonths(): Array<String> {
        val dateFormatSymbols = DateFormatSymbols()
        return dateFormatSymbols.shortMonths
    }

    internal fun getMonthIndex(month: String?) = getMonths()
        .indexOf(getMonths().find { it == month })

    private fun getYears(): Array<String> {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        val years = mutableListOf<String>()
        for (year in calendar.get(Calendar.YEAR).minus(5)..calendar.get(Calendar.YEAR).plus(6)) {
            years.add(year.toString())
        }
        return years.toTypedArray()
    }

    internal fun getItems(tabPosition: Int, calendar: Calendar): List<Item> {
        val items = mutableListOf<Item>()
        if (tabPosition == 0) {
            getMonths().forEach { name ->
                items.add(
                    Item.Month(
                        name,
                        name == calendar.getDisplayName(
                            Calendar.MONTH,
                            Calendar.SHORT,
                            Locale.ENGLISH
                        )
                    )
                )
            }
        } else {
            getYears()
                .forEach { name ->
                    items.add(
                        Item.Year(
                            name,
                            name == calendar.get(Calendar.YEAR).toString()
                        )
                    )
                }
        }
        return items
    }

    fun getMonth(calendar: Calendar, format: Format = Format.LONG): String? {
        return calendar.getDisplayName(
            Calendar.MONTH,
            if (format == Format.LONG) Calendar.LONG else Calendar.SHORT,
            Locale.ENGLISH
        )
    }

    fun getYear(calendar: Calendar): String? {
        return calendar.get(Calendar.YEAR).toString()
    }

    fun getMonthYearDisplay(context: Context, calendar: Calendar, format: Format = Format.LONG) =
        context.resources.getString(
            R.string.month_year, getMonth(calendar, format), getYear(calendar)
        )
}