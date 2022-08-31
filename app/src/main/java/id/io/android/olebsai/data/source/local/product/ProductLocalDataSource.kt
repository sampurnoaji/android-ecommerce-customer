package id.io.android.olebsai.data.source.local.product

import id.io.android.olebsai.data.model.entity.product.ProductEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductLocalDataSource @Inject constructor(private val dao: ProductDao) {

    fun getBasketProducts(): Flow<List<ProductEntity>> {
        return dao.getBasketProducts()
    }

    suspend fun insertProductToBasket(productEntity: ProductEntity) {
        dao.insertProductToBasket(productEntity)
    }
}