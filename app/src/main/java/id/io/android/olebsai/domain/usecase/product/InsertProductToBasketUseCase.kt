package id.io.android.olebsai.domain.usecase.product

import id.io.android.olebsai.core.BaseUseCase
import id.io.android.olebsai.domain.model.product.Product
import id.io.android.olebsai.domain.repository.ProductRepository
import javax.inject.Inject

class InsertProductToBasketUseCase @Inject constructor(
    private val repository: ProductRepository
) : BaseUseCase<Product, Any> {

    override suspend fun invoke(params: Product): Any {
        repository.insertProductToBasket(params)
        return true
    }
}