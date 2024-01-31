package id.io.android.olebsai.domain.model.product

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlin.math.roundToInt

@Parcelize
data class WProduct(
    val approvedBy: String,
    val beratGram: Int,
    val deskripsi: String,
    val hargaNormal: Long,
    val hargaPromo: Long,
    val isHargaPromo: Boolean,
    val kategoriId: String,
    val listPicture: List<Picture>,
    val namaProduk: String,
    val produkId: String,
    val qtyStock: Int,
    val qtyTerjual: Int,
    val status: String,
    val subKategoriId: String,
    val tokoId: String,
    val namaToko: String,
    val namaKategori: String,
    val namaSubKategori: String,
): Parcelable {

    @Parcelize
    data class Picture(
        val namaAlias: String,
        val parentId: String,
        val parentType: String,
        val pictureId: String,
        val url: String
    ): Parcelable

    fun discount(): Int = 100 - (hargaPromo.toFloat() / hargaNormal * 100).roundToInt()
}