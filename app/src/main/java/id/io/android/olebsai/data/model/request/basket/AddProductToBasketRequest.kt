package id.io.android.olebsai.data.model.request.basket


import com.squareup.moshi.Json

data class AddProductToBasketRequest(
    @field:Json(name = "catatan")
    val catatan: String,
    @field:Json(name = "produkId")
    val produkId: String,
    @field:Json(name = "qty")
    val qty: Int,
    @field:Json(name = "tokoId")
    val tokoId: String,
)