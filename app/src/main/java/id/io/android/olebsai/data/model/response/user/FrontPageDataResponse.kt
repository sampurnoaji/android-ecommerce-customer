package id.io.android.olebsai.data.model.response.user


import com.squareup.moshi.Json
import id.io.android.olebsai.data.model.response.product.ProductResponse
import id.io.android.olebsai.domain.model.user.FrontPageData

data class FrontPageDataResponse(
    @field:Json(name = "featuredProduk")
    val featuredProduk: List<ProductResponse>? = null,
    @field:Json(name = "iklanAtas")
    val iklanAtas: Any? = null,
    @field:Json(name = "iklanBawah")
    val iklanBawah: Any? = null,
    @field:Json(name = "karusel")
    val karusel: Any? = null
) {
    fun toDomain() = FrontPageData(
        featuredProduk = featuredProduk?.map {
            it.toDomain()
        }.orEmpty(),
    )
}