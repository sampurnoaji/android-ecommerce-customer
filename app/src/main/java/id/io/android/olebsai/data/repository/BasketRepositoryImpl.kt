package id.io.android.olebsai.data.repository

import id.io.android.olebsai.data.source.remote.basket.BasketRemoteDataSource
import id.io.android.olebsai.domain.model.basket.BasketItem
import id.io.android.olebsai.domain.repository.BasketRepository
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.remote.ResponseHelper
import javax.inject.Inject

class BasketRepositoryImpl @Inject constructor(
    private val remoteDataSource: BasketRemoteDataSource,
) : BasketRepository, ResponseHelper() {

    override suspend fun addProductToBasket(
        productId: String,
        qty: Int,
        tokoId: String,
        catatan: String
    ): LoadState<String> {
        return map { remoteDataSource.addProductToBasket(
            productId = productId,
            qty = qty,
            tokoId = tokoId,
            catatan = catatan
        ) }
    }

    override suspend fun getBasket(): LoadState<List<BasketItem>> {
        return map { remoteDataSource.getBasket().map { it.toDomain() } }
    }

    override suspend fun updateQty(basketId: String, qty: Int): LoadState<String> {
        return map { remoteDataSource.updateQty(basketId, qty) }
    }

    override suspend fun updateNote(basketId: String, note: String): LoadState<String> {
        return map { remoteDataSource.updateNote(basketId, note) }
    }

    override suspend fun removeProduct(productId: String): LoadState<String> {
        return map { remoteDataSource.removeProduct(productId) }
    }
}