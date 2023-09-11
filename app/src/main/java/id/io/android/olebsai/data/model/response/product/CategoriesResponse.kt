package id.io.android.olebsai.data.model.response.product

import com.squareup.moshi.Json
import id.io.android.olebsai.domain.model.product.Category

data class CategoriesResponse(
    @field:Json(name = "data")
    val data: List<CategoryResponse>
) {
    data class CategoryResponse(
        @field:Json(name = "kategoriId")
        val kategoriId: String? = null,
        @field:Json(name = "namaKategori")
        val namaKategori: String? = null,
        @field:Json(name = "gambarIcon")
        val gambarIcon: String? = null,
        @field:Json(name = "keterangan")
        val keterangan: String? = null,
    ) {
        fun toDomain() = Category(
            kategoriId = kategoriId.orEmpty(),
            namaKategori = namaKategori.orEmpty(),
            gambarIcon = gambarIcon.orEmpty(),
            keterangan = keterangan.orEmpty(),
        )
    }
}
