package id.io.android.olebsai.data.model.entity.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.io.android.olebsai.domain.model.product.Product
import kotlin.math.roundToInt

@Entity
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "entityId")
    val entityId: Int? = null,
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @ColumnInfo(name = "name")
    val name: String? = null,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String? = null,
    @ColumnInfo(name = "price")
    val price: Long? = null,
    @ColumnInfo(name = "originalPrice")
    val originalPrice: Long? = null,
    @ColumnInfo(name = "percentDiscount")
    val percentDiscount: Float? = null,
    @ColumnInfo(name = "rating")
    val rating: Float? = null,
    @ColumnInfo(name = "soldCount")
    val soldCount: Int? = null,
    @ColumnInfo(name = "shopName")
    val shopName: String? = null
) {
    fun toDomain() = Product(
        id = id ?: 0,
        name = name.orEmpty(),
        imageUrl = imageUrl.orEmpty(),
        price = price ?: 0,
        originalPrice = originalPrice ?: 0,
        percentDiscount = percentDiscount?.roundToInt() ?: 0,
        rating = rating ?: 5.0f,
        soldCount = soldCount ?: 0,
        shopName = shopName.orEmpty(),
    )
}
