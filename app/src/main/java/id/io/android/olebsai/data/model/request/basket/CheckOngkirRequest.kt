package id.io.android.olebsai.data.model.request.basket


import com.squareup.moshi.Json

data class CheckOngkirRequest(
    @field:Json(name = "keranjangIds")
    val keranjangIds: List<String>
)