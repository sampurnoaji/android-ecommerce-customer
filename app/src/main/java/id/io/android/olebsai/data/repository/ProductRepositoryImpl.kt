package id.io.android.olebsai.data.repository

import id.io.android.olebsai.data.source.local.product.ProductLocalDataSource
import id.io.android.olebsai.data.source.remote.product.ProductPagingSource
import id.io.android.olebsai.domain.model.product.Product
import id.io.android.olebsai.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productPagingSource: ProductPagingSource,
    private val localDataSource: ProductLocalDataSource
) : ProductRepository {

    override fun getProductPagingSource(): ProductPagingSource = productPagingSource

    override fun getBasketProducts(): Flow<List<Product>> {
        return localDataSource.getBasketProducts().map {
            it.map { entity ->
                entity.toDomain()
            }
        }
    }

    override suspend fun insertProductToBasket(product: Product) {
        localDataSource.insertProductToBasket(product.toEntity())
    }
}