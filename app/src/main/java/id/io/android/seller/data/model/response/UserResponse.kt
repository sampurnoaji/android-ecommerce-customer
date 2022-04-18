package id.io.android.seller.data.model.response


import com.squareup.moshi.Json
import id.io.android.seller.domain.model.User

data class UserResponse(
    @field:Json(name = "data")
    val `data`: Data? = null,
    @field:Json(name = "support")
    val support: Support? = null
) {
    data class Data(
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

    data class Support(
        @field:Json(name = "text")
        val text: String? = null,
        @field:Json(name = "url")
        val url: String? = null
    )

    fun toDomain(): User = User(id = data?.id ?: 0)
}