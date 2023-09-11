package id.io.android.olebsai.data.model.response.user


import com.squareup.moshi.Json

data class DistrictResponse(
    @field:Json(name = "idKota")
    val idKota: String? = null,
    @field:Json(name = "namaKota")
    val namaKota: String? = null,
)