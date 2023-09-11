package id.io.android.olebsai.data.model.response.basket


import com.squareup.moshi.Json
import id.io.android.olebsai.domain.model.order.OrderDetail
import id.io.android.olebsai.domain.model.product.OrderedProduct

data class OrderDetailResponse(
    @field:Json(name = "pesananHeader")
    val orderResponse: OrderResponse,
    @field:Json(name = "produk")
    val orderedProducts: List<OrderedProductResponse>?
) {
    fun toDomain() = OrderDetail(
        order = orderResponse.toDomain(),
        orderedProducts = orderedProducts?.map { it.toDomain() }.orEmpty()
    )
    data class OrderedProductResponse(
        @field:Json(name = "catatan")
        val catatan: String? = null,
        @field:Json(name = "hargaSatuan")
        val hargaSatuan: Long? = null,
        @field:Json(name = "hargaTotal")
        val hargaTotal: Long? = null,
        @field:Json(name = "namaProduk")
        val namaProduk: String? = null,
        @field:Json(name = "pesananHeaderId")
        val pesananHeaderId: String? = null,
        @field:Json(name = "pesananId")
        val pesananId: String? = null,
        @field:Json(name = "qty")
        val qty: Int? = null
    ) {
        fun toDomain() = OrderedProduct(
            catatan = catatan.orEmpty(),
            hargaSatuan = hargaSatuan ?: 0L,
            hargaTotal = hargaTotal ?: 0L,
            namaProduk = namaProduk.orEmpty(),
            pesananHeaderId = pesananHeaderId.orEmpty(),
            pesananId = pesananId.orEmpty(),
            qty = qty ?: 0,
        )
    }
}
