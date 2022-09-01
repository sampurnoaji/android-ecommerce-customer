package id.io.android.olebsai.domain.usecase.product

import javax.inject.Inject

data class ProductUseCases @Inject constructor(
    val getProductPagingSourceUseCase: GetProductPagingSourceUseCase,
    val getProductDetailUseCase: GetProductDetailUseCase,
    val getBasketProductsUseCase: GetBasketProductsUseCase,
    val insertProductToBasketUseCase: InsertProductToBasketUseCase
)