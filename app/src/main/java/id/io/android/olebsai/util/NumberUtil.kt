package id.io.android.olebsai.util

import java.text.NumberFormat
import java.util.Locale

val indonesianLocale = Locale("id", "ID")

fun Long.addThousandSeparator(): String {
    val numberFormat = NumberFormat.getInstance(indonesianLocale)
    return numberFormat.format(this)
}

fun Long.toRupiah(): String {
    return "Rp ${this.addThousandSeparator()}"
}