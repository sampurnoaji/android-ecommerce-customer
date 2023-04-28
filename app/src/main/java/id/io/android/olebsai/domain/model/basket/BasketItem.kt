package id.io.android.olebsai.domain.model.basket

import id.io.android.olebsai.domain.model.product.WProduct

data class BasketItem(
    val keranjangId: String,
    val catatan: String,
    val qtyPembelian: Int,
    val product: WProduct,
)