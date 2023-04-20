package id.io.android.olebsai.domain.usecase.product

import javax.inject.Inject

data class ProductUseCases @Inject constructor(
    val getProductDetailUseCase: GetProductDetailUseCase,
    val getBasketProductsUseCase: GetBasketProductsUseCase,
    val insertProductToBasketUseCase: InsertProductToBasketUseCase,
    val deleteBasketProductUseCase: DeleteBasketProductUseCase,
)