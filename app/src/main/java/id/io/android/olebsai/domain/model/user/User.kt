package id.io.android.olebsai.domain.model.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String,
    val username: String,
    val email: String,
    val otpFlag: Boolean,
    val phone: String,
    val address: Address,
): Parcelable {

    @Parcelize
    data class Address(
        val alamat: String,
        val kecamatan: String,
        val kecamatanId: String,
        val kodePos: String,
        val kota: String,
        val kotaId: String,
        val provinsi: String,
        val provinsiId: String,
    ): Parcelable
}