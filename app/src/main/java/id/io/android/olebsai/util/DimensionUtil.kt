package id.io.android.olebsai.util

import android.util.DisplayMetrics

fun Int.dpToPx(displayMetrics: DisplayMetrics): Int = (this * displayMetrics.density).toInt()