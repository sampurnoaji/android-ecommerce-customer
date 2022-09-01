package id.io.android.olebsai.data.model.response.product

import com.squareup.moshi.Json
import id.io.android.olebsai.domain.model.product.Product
import kotlin.math.roundToInt

data class ProductDetailResponse(
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
    @field:Json(name = "shopName")
    val shopName: String? = null,
    @field:Json(name = "condition")
    val condition: String? = null,
    @field:Json(name = "dimension")
    val dimension: String? = null,
    @field:Json(name = "minOrder")
    val minOrder: String? = null,
    @field:Json(name = "category")
    val category: String? = null,
    @field:Json(name = "description")
    val description: String? = null,
) {
    fun toDomain(): Product = Product(
        id = id ?: 0,
        name = name.orEmpty(),
        imageUrl = imageUrl.orEmpty(),
        price = price ?: 0,
        originalPrice = originalPrice ?: 0,
        percentDiscount = percentDiscount?.roundToInt() ?: 0,
        rating = rating ?: 5.0f,
        soldCount = soldCount ?: 0,
        shopName = shopName.orEmpty(),
        condition = condition.orEmpty(),
        dimension = dimension.orEmpty(),
        minOrder = minOrder.orEmpty(),
        category = category.orEmpty(),
        description = description.orEmpty()
    )
}