package id.io.android.olebsai.domain.model.user

data class RegisterParams(
    val email: String,
    val name: String,
    val phoneNumber: String,
    val password: String
)
