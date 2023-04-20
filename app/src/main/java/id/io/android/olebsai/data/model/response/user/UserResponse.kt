package id.io.android.olebsai.data.model.response.user


import com.squareup.moshi.Json

data class UserResponse(
    @field:Json(name = "avatar")
    val avatar: String? = null,
    @field:Json(name = "email")
    val email: String? = null,
    @field:Json(name = "first_name")
    val firstName: String? = null,
    @field:Json(name = "id")
    val id: Int? = null,
    @field:Json(name = "last_name")
    val lastName: String? = null
)