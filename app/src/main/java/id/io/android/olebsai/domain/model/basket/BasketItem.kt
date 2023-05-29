package id.io.android.olebsai.domain.model.basket

import android.os.Parcelable
import id.io.android.olebsai.domain.model.product.WProduct
import kotlinx.parcelize.Parcelize

@Parcelize
data class BasketItem(
    val keranjangId: String,
    val catatan: String,
    val qtyPembelian: Int,
    val product: WProduct,
): Parcelable