package id.io.android.olebsai.data.model.response.user


import com.squareup.moshi.Json
import id.io.android.olebsai.domain.model.user.User

data class UserResponse(
    @field:Json(name = "gambarProfile")
    val avatar: String? = null,
    @field:Json(name = "email")
    val email: String? = null,
    @field:Json(name = "namaUser")
    val username: String? = null,
    @field:Json(name = "userId")
    val userId: String? = null,
    @field:Json(name = "otpFlag")
    val otpFlag: Boolean? = null,
    @field:Json(name = "nomorHp")
    val phone: String? = null,
    @field:Json(name = "alamat")
    val alamat: Alamat? = null,
) {
    data class Alamat(
        @field:Json(name = "alamat")
        val alamat: String? = null,
        @field:Json(name = "kecamatan")
        val kecamatan: String? = null,
        @field:Json(name = "kecamatanId")
        val kecamatanId: String? = null,
        @field:Json(name = "kodePos")
        val kodePos: String? = null,
        @field:Json(name = "kota")
        val kota: String? = null,
        @field:Json(name = "kotaId")
        val kotaId: String? = null,
        @field:Json(name = "provinsi")
        val provinsi: String? = null,
        @field:Json(name = "provinsiId")
        val provinsiId: String? = null,
    )
    fun toDomain() = User(
        id = userId.orEmpty(),
        username = username.orEmpty(),
        email = email.orEmpty(),
        otpFlag = otpFlag ?: false,
        phone = phone.orEmpty(),
        address = User.Address(
            alamat = alamat?.alamat.orEmpty(),
            kecamatan = alamat?.kecamatan.orEmpty(),
            kecamatanId = alamat?.kecamatanId.orEmpty(),
            kodePos = alamat?.kodePos.orEmpty(),
            kota = alamat?.kota.orEmpty(),
            kotaId = alamat?.kotaId.orEmpty(),
            provinsi = alamat?.provinsi.orEmpty(),
            provinsiId = alamat?.provinsiId.orEmpty(),
        )
    )
}