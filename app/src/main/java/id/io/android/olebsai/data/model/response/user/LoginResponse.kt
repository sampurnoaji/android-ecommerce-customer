package id.io.android.olebsai.data.model.response.user


import com.squareup.moshi.Json
import id.io.android.olebsai.data.model.response.user.UserResponse.Alamat
import id.io.android.olebsai.domain.model.user.User

data class LoginResponse(
    @field:Json(name = "email")
    val email: String? = null,
    @field:Json(name = "nama")
    val nama: String? = null,
    @field:Json(name = "role")
    val role: String? = null,
    @field:Json(name = "token")
    val token: String? = null,
    @field:Json(name = "otpFlag")
    val otpFlag: Boolean? = null,
    @field:Json(name = "alamat")
    val alamat: Alamat? = null,
) {
    fun toDomain() = User(
        id = "",
        username = nama.orEmpty(),
        email = email.orEmpty(),
        otpFlag = otpFlag ?: false,
        phone = "",
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