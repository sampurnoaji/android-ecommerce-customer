package id.io.android.olebsai.domain.model.product

data class Product(
    val id: Int,
    val imageUrl: String,
    val name: String,
    val price: Long,
    val originalPrice: Long,
    val percentDiscount: Int,
    val rating: Float,
    val soldCount: Int
)