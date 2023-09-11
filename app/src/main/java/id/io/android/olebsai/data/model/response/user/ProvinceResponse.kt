package id.io.android.olebsai.data.model.response.user


import com.squareup.moshi.Json

data class ProvinceResponse(
    @field:Json(name = "idPropinsi")
    val idPropinsi: String? = null,
    @field:Json(name = "namaPropinsi")
    val namaPropinsi: String? = null,
)