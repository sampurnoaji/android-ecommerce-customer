package id.io.android.olebsai.domain.model.user

data class LoginParams(
    val email: String,
    val password: String? = null,
    val otp: String? = null
)
