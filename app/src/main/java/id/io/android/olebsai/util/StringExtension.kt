package id.io.android.olebsai.util

import android.util.Patterns

fun String.isValidEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}