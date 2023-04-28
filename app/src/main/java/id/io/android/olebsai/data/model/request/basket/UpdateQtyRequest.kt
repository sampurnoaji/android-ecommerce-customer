package id.io.android.olebsai.data.model.request.basket


import com.squareup.moshi.Json

data class UpdateQtyRequest(
    @field:Json(name = "keranjangId")
    val keranjangId: String,
    @field:Json(name = "qty")
    val qty: Int,
)