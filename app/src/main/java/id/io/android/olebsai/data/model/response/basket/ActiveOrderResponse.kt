package id.io.android.olebsai.data.model.response.basket


import com.squareup.moshi.Json

data class ActiveOrderResponse(
    @field:Json(name = "data")
    val `data`: List<OrderResponse>
)