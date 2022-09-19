package id.io.android.olebsai.domain.model.address

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val id: Int,
    val label: String,
    val name: String,
    val phone: String,
    val address: String,
    val note: String
) : Parcelable
