package id.io.android.olebsai.data.model.request.basket


import com.squareup.moshi.Json

data class RemoveProductRequest(
    @field:Json(name = "produkId")
    val produkId: String,
)