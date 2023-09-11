package id.io.android.olebsai.domain.model.basket

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Courier(
    val gambarLogo: String,
    val kodeKurir: String,
    val kurirId: String,
    val namaKurir: String,
    val services: List<Service>
) : Parcelable {

    @Parcelize
    data class Service(
        val deskripsi: String,
        val estimasiSampai: String,
        val namaServis: String,
        val ongkir: Long
    ) : Parcelable
}
