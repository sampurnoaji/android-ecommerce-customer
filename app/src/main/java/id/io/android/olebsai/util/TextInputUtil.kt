package id.io.android.olebsai.util

import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputLayout
import id.io.android.olebsai.R


fun TextInputLayout.setErrorText(@StringRes stringRes: Int = R.string.form_required_field) {
    error = context.getString(stringRes)
}

fun TextInputLayout.clearError() {
    error = null
}