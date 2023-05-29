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
) {
    fun toDomain() = User(
        id = userId.orEmpty(),
        username = username.orEmpty(),
        email = email.orEmpty(),
        otpFlag = otpFlag ?: false,
        phone = phone.orEmpty(),
    )
}