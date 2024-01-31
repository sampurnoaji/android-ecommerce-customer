package id.io.android.olebsai.data.model.response.basket


import com.squareup.moshi.Json
import id.io.android.olebsai.domain.model.order.Order
import id.io.android.olebsai.domain.model.order.Order.Status.BELUM_BAYAR
import id.io.android.olebsai.domain.model.order.Order.Status.DIKEMAS
import id.io.android.olebsai.domain.model.order.Order.Status.DIKIRIM
import id.io.android.olebsai.domain.model.order.Order.Status.DITERIMA
import id.io.android.olebsai.domain.model.order.Order.Status.SELESAI
import id.io.android.olebsai.domain.model.order.Order.Status.UNDEFINED

data class OrderResponse(
    @field:Json(name = "administrasi")
    val administrasi: Long? = null,
    @field:Json(name = "belanja")
    val belanja: Long? = null,
    @field:Json(name = "estimasiSampai")
    val estimasiSampai: String? = null,
    @field:Json(name = "headerId")
    val headerId: String? = null,
    @field:Json(name = "kotaToko")
    val kotaToko: String? = null,
    @field:Json(name = "namaJasaPengiriman")
    val namaJasaPengiriman: String? = null,
    @field:Json(name = "namaToko")
    val namaToko: String? = null,
    @field:Json(name = "nomorResi")
    val nomorResi: String? = null,
    @field:Json(name = "ongkir")
    val ongkir: Long? = null,
    @field:Json(name = "servisJasaPengiriman")
    val servisJasaPengiriman: String? = null,
    @field:Json(name = "status")
    val status: String? = null,
    @field:Json(name = "tglBayar")
    val tglBayar: String? = null,
    @field:Json(name = "tglCheckout")
    val tglCheckout: String? = null,
    @field:Json(name = "tokoId")
    val tokoId: String? = null,
    @field:Json(name = "totalBayar")
    val totalBayar: Long? = null,
    @field:Json(name = "nomorPesanan")
    val nomorPesanan: String? = null,
) {
    fun toDomain() = Order(
        administrasi = administrasi ?: 0L,
        belanja = belanja ?: 0L,
        estimasiSampai = estimasiSampai.orEmpty(),
        headerId = headerId.orEmpty(),
        kotaToko = kotaToko.orEmpty(),
        namaJasaPengiriman = namaJasaPengiriman.orEmpty(),
        namaToko = namaToko.orEmpty(),
        nomorResi = nomorResi.orEmpty(),
        ongkir = ongkir ?: 0L,
        servisJasaPengiriman = servisJasaPengiriman.orEmpty(),
        status = when (status) {
            "BELUM BAYAR" -> BELUM_BAYAR
            "DIKEMAS" -> DIKEMAS
            "DIKIRIM" -> DIKIRIM
            "DITERIMA" -> DITERIMA
            "SELESAI" -> SELESAI
            else -> UNDEFINED
        },
        tglBayar = tglBayar.orEmpty(),
        tglCheckout = tglCheckout.orEmpty(),
        tokoId = tokoId.orEmpty(),
        totalBayar = totalBayar ?: 0L,
        nomorPesanan = nomorPesanan.orEmpty(),
    )
}