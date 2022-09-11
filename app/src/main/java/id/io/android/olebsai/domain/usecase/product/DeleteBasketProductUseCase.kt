package id.io.android.olebsai.domain.usecase.product

import id.io.android.olebsai.core.BaseUseCase
import id.io.android.olebsai.domain.repository.ProductRepository
import javax.inject.Inject

class DeleteBasketProductUseCase @Inject constructor(
    private val repository: ProductRepository
) : BaseUseCase<Int, Any> {

    override suspend fun invoke(params: Int): Any {
        repository.deleteProductBasket(params)
        return true
    }
}