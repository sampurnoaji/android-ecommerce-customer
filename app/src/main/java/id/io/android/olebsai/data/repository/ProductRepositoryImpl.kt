package id.io.android.olebsai.data.repository

import id.io.android.olebsai.data.source.remote.product.ProductPagingSource
import id.io.android.olebsai.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productPagingSource: ProductPagingSource
) : ProductRepository {

    override fun getProductPagingSource(): ProductPagingSource = productPagingSource
}