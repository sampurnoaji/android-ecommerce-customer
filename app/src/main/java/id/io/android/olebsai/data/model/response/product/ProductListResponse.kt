package id.io.android.olebsai.data.model.response.product


import com.squareup.moshi.Json

data class ProductListResponse(
    @field:Json(name = "data")
    val products: List<ProductResponse>? = null
)