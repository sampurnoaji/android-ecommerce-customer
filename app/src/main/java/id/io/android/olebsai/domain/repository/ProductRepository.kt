package id.io.android.olebsai.domain.repository

import id.io.android.olebsai.data.source.remote.product.ProductPagingSource
import id.io.android.olebsai.domain.model.product.Product
import id.io.android.olebsai.util.LoadState
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProductPagingSource(): ProductPagingSource
    suspend fun getProductDetail(id: Int): LoadState<Product>
    fun getBasketProducts(): Flow<List<Product>>
    suspend fun insertProductToBasket(product: Product)
    suspend fun deleteProductBasket(productId: Int)
}