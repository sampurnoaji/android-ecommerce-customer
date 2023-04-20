package id.io.android.olebsai.data.model.request.user.register


import com.squareup.moshi.Json

data class RegisterRequest(
    @field:Json(name = "email")
    val email: String,
    @field:Json(name = "namaUser")
    val namaUser: String,
    @field:Json(name = "nomorHp")
    val nomorHp: String,
    @field:Json(name = "password")
    val password: String
)