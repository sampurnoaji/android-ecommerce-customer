package id.io.android.olebsai.domain.usecase.product

import id.io.android.olebsai.data.source.remote.product.ProductPagingSource
import id.io.android.olebsai.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductPagingSourceUseCase @Inject constructor(private val repository: ProductRepository) {
    operator fun invoke(): ProductPagingSource = repository.getProductPagingSource()
}