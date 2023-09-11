package id.io.android.olebsai.domain.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApiAddress(
    val id: String,
    val name: String,
): Parcelable
