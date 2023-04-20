package id.io.android.olebsai.data.model.response

import com.squareup.moshi.Json

data class BaseResponse<T>(
    @field:Json(name = "code")
    val code: Int? = null,
    @field:Json(name = "data")
    val `data`: T?,
    @field:Json(name = "message")
    val message: String? = null,
    @field:Json(name = "success")
    val success: String? = null
)