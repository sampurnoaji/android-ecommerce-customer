package id.io.android.olebsai.data.model.request.user


import com.squareup.moshi.Json
import id.io.android.olebsai.data.model.request.user.register.RegisterRequest

data class UpdateProfileRequest(
    @field:Json(name = "email")
    val email: String,
    @field:Json(name = "namaUser")
    val namaUser: String,
    @field:Json(name = "nomorHp")
    val nomorHp: String,
    @field:Json(name = "alamat")
    val alamat: RegisterRequest.Alamat
)
