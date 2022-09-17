package id.io.android.olebsai.presentation.order

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductCheckout(
    val shopName: String,
    val qty: Int,
    val imageUrl: String,
    val name: String,
    val total: Long,
    val originalPrice: Long,
    val discount: Int
) : Parcelable
