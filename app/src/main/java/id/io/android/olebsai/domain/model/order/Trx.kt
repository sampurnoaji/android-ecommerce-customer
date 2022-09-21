package id.io.android.olebsai.domain.model.order

import id.io.android.olebsai.domain.model.product.Product

data class Trx(
    val id: Int,
    val date: String,
    val status: String,
    val products: List<Product>,
)
