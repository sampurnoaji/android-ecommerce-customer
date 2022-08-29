package id.io.android.olebsai.domain.repository

import id.io.android.olebsai.data.source.remote.product.ProductPagingSource

interface ProductRepository {
    fun getProductPagingSource(): ProductPagingSource
}