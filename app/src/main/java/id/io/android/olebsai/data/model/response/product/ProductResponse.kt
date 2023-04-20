package id.io.android.olebsai.data.model.response.product

import com.squareup.moshi.Json
import id.io.android.olebsai.domain.model.product.WProduct

data class ProductResponse(
    @field:Json(name = "approvedBy")
    val approvedBy: Any? = null,
    @field:Json(name = "deskripsi")
    val deskripsi: String? = null,
    @field:Json(name = "hargaNormal")
    val hargaNormal: Long? = null,
    @field:Json(name = "hargaPromo")
    val hargaPromo: Long? = null,
    @field:Json(name = "isHargaPromo")
    val isHargaPromo: Boolean? = null,
    @field:Json(name = "kategoriId")
    val kategoriId: String? = null,
    @field:Json(name = "listPicture")
    val listPicture: List<Picture?>? = null,
    @field:Json(name = "namaProduk")
    val namaProduk: String? = null,
    @field:Json(name = "produkId")
    val produkId: String? = null,
    @field:Json(name = "qtyStock")
    val qtyStock: Int? = null,
    @field:Json(name = "qtyTerjual")
    val qtyTerjual: Int? = null,
    @field:Json(name = "status")
    val status: String? = null,
    @field:Json(name = "subKategoriId")
    val subKategoriId: String? = null,
    @field:Json(name = "tokoId")
    val tokoId: String? = null,
    @field:Json(name = "namaToko")
    val namaToko: String? = null,
    @field:Json(name = "namaKategori")
    val namaKategori: String? = null,
    @field:Json(name = "namaSubKategori")
    val namaSubKategori: String? = null
) {
    data class Picture(
        @field:Json(name = "namaAlias")
        val namaAlias: String? = null,
        @field:Json(name = "parentId")
        val parentId: String? = null,
        @field:Json(name = "parentType")
        val parentType: String? = null,
        @field:Json(name = "pictureId")
        val pictureId: String? = null,
        @field:Json(name = "url")
        val url: String? = null
    )

    fun toDomain() = WProduct(
        approvedBy = approvedBy ?: Any(),
        deskripsi = deskripsi.orEmpty(),
        hargaNormal = hargaNormal ?: 0,
        hargaPromo = hargaPromo ?: 0,
        isHargaPromo = isHargaPromo ?: false,
        kategoriId = kategoriId.orEmpty(),
        listPicture = listPicture?.map {
            WProduct.Picture(
                namaAlias = it?.namaAlias.orEmpty(),
                parentId = it?.parentId.orEmpty(),
                parentType = it?.parentType.orEmpty(),
                pictureId = it?.pictureId.orEmpty(),
                url = it?.url.orEmpty(),
            )
        }.orEmpty(),
        namaProduk = namaProduk.orEmpty(),
        produkId = produkId.orEmpty(),
        qtyStock = qtyStock ?: 0,
        qtyTerjual = qtyTerjual ?: 0,
        status = status.orEmpty(),
        subKategoriId = subKategoriId.orEmpty(),
        tokoId = tokoId.orEmpty(),
        namaToko = namaToko.orEmpty(),
        namaKategori = namaKategori.orEmpty(),
        namaSubKategori = namaSubKategori.orEmpty(),
    )
}