package id.io.android.olebsai.data.model.response.product


import com.squareup.moshi.Json

data class ProductListResponse(
    @field:Json(name = "products")
    val products: List<ProductDetailResponse>? = null
)