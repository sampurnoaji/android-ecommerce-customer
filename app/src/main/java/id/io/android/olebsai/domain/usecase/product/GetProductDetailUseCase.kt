package id.io.android.olebsai.domain.usecase.product

import id.io.android.olebsai.core.BaseUseCase
import id.io.android.olebsai.domain.model.product.WProduct
import id.io.android.olebsai.domain.repository.ProductRepository
import id.io.android.olebsai.util.LoadState
import javax.inject.Inject

class GetProductDetailUseCase @Inject constructor(
    private val repository: ProductRepository
): BaseUseCase<String, LoadState<WProduct>> {

    override suspend fun invoke(params: String): LoadState<WProduct> {
        return repository.getProductDetail(params)
    }
}