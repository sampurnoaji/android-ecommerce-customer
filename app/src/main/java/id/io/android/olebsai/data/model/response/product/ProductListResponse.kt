package id.io.android.olebsai.data.model.response.product


import com.squareup.moshi.Json
import kotlin.math.roundToInt
import id.io.android.olebsai.domain.model.product.Product as ProductDomain

data class ProductListResponse(
    @field:Json(name = "products")
    val products: List<Product>? = null
) {
    data class Product(
        @field:Json(name = "id")
        val id: Int? = null,
        @field:Json(name = "name")
        val name: String? = null,
        @field:Json(name = "imageUrl")
        val imageUrl: String? = null,
        @field:Json(name = "price")
        val price: Long? = null,
        @field:Json(name = "originalPrice")
        val originalPrice: Long? = null,
        @field:Json(name = "percentDiscount")
        val percentDiscount: Float? = null,
        @field:Json(name = "rating")
        val rating: Float? = null,
        @field:Json(name = "soldCount")
        val soldCount: Int? = null,
    ) {
        fun toDomain(): ProductDomain = ProductDomain(
            id = id ?: 0,
            name = name.orEmpty(),
            imageUrl = imageUrl.orEmpty(),
            price = price ?: 0,
            originalPrice = originalPrice ?: 0,
            percentDiscount = percentDiscount?.roundToInt() ?: 0,
            rating = rating ?: 5.0f,
            soldCount = soldCount ?: 0
        )
    }
}