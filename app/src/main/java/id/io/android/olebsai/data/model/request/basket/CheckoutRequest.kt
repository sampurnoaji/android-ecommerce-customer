package id.io.android.olebsai.data.model.request.basket


import com.squareup.moshi.Json

data class CheckoutRequest(
    @field:Json(name = "keranjangIds")
    val keranjangIds: List<String>,
    @field:Json(name = "estimasiSampai")
    val estimasiSampai: String,
    @field:Json(name = "namaJasaPengiriman")
    val namaJasaPengiriman: String,
    @field:Json(name = "ongkir")
    val ongkir: Long,
    @field:Json(name = "servisJasaPengiriman")
    val servisJasaPengiriman: String,
)