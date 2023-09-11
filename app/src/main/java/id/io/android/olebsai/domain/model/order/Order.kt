package id.io.android.olebsai.domain.model.order

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    val administrasi: Long,
    val belanja: Long,
    val estimasiSampai: String,
    val headerId: String,
    val kotaToko: String,
    val namaJasaPengiriman: String,
    val namaToko: String,
    val nomorResi: String,
    val ongkir: Long,
    val servisJasaPengiriman: String,
    val status: Status,
    val tglBayar: String,
    val tglCheckout: String,
    val tokoId: String,
    val totalBayar: Long,
    val nomorPesanan: String,
) : Parcelable {
    enum class Status(val status: String) {
        BELUM_BAYAR("BELUM BAYAR"),
        DIKEMAS("DIKEMAS"),
        DIKIRIM("DIKIRIM"),
        DITERIMA("DITERIMA"),
        SELESAI("SELESAI"),
        UNDEFINED(""),
    }
}
