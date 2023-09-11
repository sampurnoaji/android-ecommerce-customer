package id.io.android.olebsai.domain.repository

import androidx.paging.PagingData
import id.io.android.olebsai.data.source.remote.product.ProductPagingSource
import id.io.android.olebsai.domain.model.product.Category
import id.io.android.olebsai.domain.model.product.Product
import id.io.android.olebsai.domain.model.product.WProduct
import id.io.android.olebsai.util.LoadState
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProductList(query: String, kategoriId: String?): Flow<PagingData<WProduct>>
    fun getProductPagingSource(query: String, kategoriId: String?): ProductPagingSource
    suspend fun getProductDetail(id: String): LoadState<WProduct>
    fun getBasketProducts(): Flow<List<Product>>
    suspend fun insertProductToBasket(product: Product)
    suspend fun deleteProductBasket(productId: Int)
    suspend fun getCategories(): LoadState<List<Category>>
}