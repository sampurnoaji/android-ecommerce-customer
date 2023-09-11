package id.io.android.olebsai.data.model.response.user


import com.squareup.moshi.Json

data class SubDistrictResponse(
    @field:Json(name = "idKecamatan")
    val idKecamatan: String? = null,
    @field:Json(name = "namaKecamatan")
    val namaKecamatan: String? = null,
)