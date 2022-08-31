package id.io.android.olebsai.domain.repository

import id.io.android.olebsai.data.source.remote.product.ProductPagingSource
import id.io.android.olebsai.domain.model.product.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProductPagingSource(): ProductPagingSource
    fun getBasketProducts(): Flow<List<Product>>
    suspend fun insertProductToBasket(product: Product)
}