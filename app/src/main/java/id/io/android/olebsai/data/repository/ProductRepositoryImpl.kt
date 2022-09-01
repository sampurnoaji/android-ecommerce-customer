package id.io.android.olebsai.data.repository

import id.io.android.olebsai.data.source.local.product.ProductLocalDataSource
import id.io.android.olebsai.data.source.remote.product.ProductPagingSource
import id.io.android.olebsai.data.source.remote.product.ProductRemoteDataSource
import id.io.android.olebsai.domain.model.product.Product
import id.io.android.olebsai.domain.repository.ProductRepository
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.remote.ResponseHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productPagingSource: ProductPagingSource,
    private val remoteDataSource: ProductRemoteDataSource,
    private val localDataSource: ProductLocalDataSource
) : ProductRepository, ResponseHelper() {

    override fun getProductPagingSource(): ProductPagingSource = productPagingSource

    override suspend fun getProductDetail(id: Int): LoadState<Product> {
        return map { remoteDataSource.getProductDetail(id).toDomain() }
    }

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