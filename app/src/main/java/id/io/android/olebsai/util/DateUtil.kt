package id.io.android.olebsai.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val localeIndonesia = Locale("ID", "id")

const val serverPattern = "yyyy-MM-dd HH:mm:ss"
const val longHourPattern = "d MMMM yyyy • HH:mm"
const val shortHourPattern = "d MMM yyyy • HH:mm"
const val longPattern = "d MMMM yyyy"
const val shortPattern = "d MMM yyyy"

fun String.toUi(outPattern: String = longHourPattern, inPattern: String = serverPattern): String {
    return try {
        var formatter = SimpleDateFormat(inPattern, localeIndonesia)
        val date = formatter.parse(this)
        formatter = SimpleDateFormat(outPattern, localeIndonesia)
        formatter.format(date as Date)
    } catch (e: Exception) {
        this
    }
}