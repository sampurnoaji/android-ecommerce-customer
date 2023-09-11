package id.io.android.olebsai.domain.model.order

import id.io.android.olebsai.domain.model.product.OrderedProduct

data class OrderDetail(
    val order: Order,
    val orderedProducts: List<OrderedProduct>,
)
