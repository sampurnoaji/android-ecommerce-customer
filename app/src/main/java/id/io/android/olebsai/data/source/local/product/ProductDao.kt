package id.io.android.olebsai.data.source.local.product

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.io.android.olebsai.data.model.entity.product.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM ProductEntity")
    fun getBasketProducts(): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductToBasket(productEntity: ProductEntity)

    @Query("DELETE FROM ProductEntity WHERE id = :productId")
    suspend fun deleteBasketProduct(productId: Int)
}