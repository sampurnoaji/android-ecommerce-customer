package id.io.android.olebsai.domain.model.product

import id.io.android.olebsai.data.model.entity.product.ProductEntity

data class Product(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val price: Long,
    val originalPrice: Long,
    val percentDiscount: Int,
    val rating: Float,
    val soldCount: Int,
    val shopName: String,
) {
    fun toEntity() = ProductEntity(
        id = id,
        name = name,
        imageUrl = imageUrl,
        price = price,
        originalPrice = originalPrice,
        percentDiscount = percentDiscount.toFloat(),
        rating = rating,
        soldCount = soldCount,
        shopName = shopName,
    )
}