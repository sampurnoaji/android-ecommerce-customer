package id.io.android.olebsai.domain.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val username: String,
    val email: String,
    val otpFlag: Boolean,
): Parcelable