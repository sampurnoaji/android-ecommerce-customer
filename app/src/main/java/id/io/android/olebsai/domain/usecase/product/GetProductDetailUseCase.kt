package id.io.android.olebsai.domain.usecase.product

import id.io.android.olebsai.core.BaseUseCase
import id.io.android.olebsai.domain.model.product.Product
import id.io.android.olebsai.domain.repository.ProductRepository
import id.io.android.olebsai.util.LoadState
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val repository: ProductRepository
): BaseUseCase<Int, LoadState<Product>> {

    override suspend fun invoke(params: Int): LoadState<Product> {
        return repository.getProductDetail(params)
    }
}