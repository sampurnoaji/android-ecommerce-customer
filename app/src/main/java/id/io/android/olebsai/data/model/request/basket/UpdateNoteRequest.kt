package id.io.android.olebsai.data.model.request.basket


import com.squareup.moshi.Json

data class UpdateNoteRequest(
    @field:Json(name = "keranjangId")
    val keranjangId: String,
    @field:Json(name = "catatan")
    val catatan: String,
)