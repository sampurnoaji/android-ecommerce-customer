package id.io.android.olebsai.domain.repository

import id.io.android.olebsai.domain.model.basket.BasketItem
import id.io.android.olebsai.util.LoadState

interface BasketRepository {
    suspend fun addProductToBasket(
        productId: String,
        qty: Int,
        tokoId: String,
        catatan: String,
    ): LoadState<String>

    suspend fun getBasket(): LoadState<List<BasketItem>>
    suspend fun updateQty(basketId: String, qty: Int): LoadState<String>
    suspend fun updateNote(basketId: String, note: String): LoadState<String>
    suspend fun removeProduct(productId: String): LoadState<String>
    suspend fun checkout(basketIds: List<String>, namaJasaPengiriman: String): LoadState<String>
}