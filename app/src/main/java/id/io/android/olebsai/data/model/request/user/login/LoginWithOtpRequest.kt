package id.io.android.olebsai.data.model.request.user.login


import com.squareup.moshi.Json

data class LoginWithOtpRequest(
    @field:Json(name = "email")
    val email: String,
    @field:Json(name = "otp")
    val otp: String
)