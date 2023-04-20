package id.io.android.olebsai.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.io.android.olebsai.data.source.local.product.ProductLocalDataSource
import id.io.android.olebsai.data.source.remote.product.ProductPagingSource
import id.io.android.olebsai.data.source.remote.product.ProductRemoteDataSource
import id.io.android.olebsai.domain.model.product.Product
import id.io.android.olebsai.domain.model.product.WProduct
import id.io.android.olebsai.domain.repository.ProductRepository
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.remote.ResponseHelper
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource,
    private val localDataSource: ProductLocalDataSource
) : ProductRepository, ResponseHelper() {

    override fun getProductList(query: String): Flow<PagingData<WProduct>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = ProductPagingSource.ITEMS_PER_PAGE
            ),
            pagingSourceFactory = { ProductPagingSource(remoteDataSource, query) }
        ).flow
    }

    override fun getProductPagingSource(query: String): ProductPagingSource =
        ProductPagingSource(remoteDataSource, query)

    override suspend fun getProductDetail(id: String): LoadState<WProduct> {
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

    override suspend fun deleteProductBasket(productId: Int) {
        localDataSource.deleteBasketProduct(productId)
    }
}