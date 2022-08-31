package id.io.android.olebsai.domain.usecase.product

import id.io.android.olebsai.core.BaseUseCase
import id.io.android.olebsai.domain.model.product.Product
import id.io.android.olebsai.domain.repository.ProductRepository
import id.io.android.olebsai.util.NoParams
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBasketProductsUseCase @Inject constructor(
    private val repository: ProductRepository
): BaseUseCase<NoParams, Flow<List<Product>>> {

    override suspend fun invoke(params: NoParams): Flow<List<Product>> {
        return repository.getBasketProducts()
    }
}