package id.io.android.olebsai.domain.repository

import id.io.android.olebsai.domain.model.basket.BasketItem
import id.io.android.olebsai.domain.model.basket.Courier
import id.io.android.olebsai.domain.model.order.Order
import id.io.android.olebsai.domain.model.order.OrderDetail
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
    suspend fun getCouriers(): LoadState<List<Courier>>
    suspend fun checkout(
        basketIds: List<String>, namaJasaPengiriman: String,
        servisJasaPengiriman: String,
        estimasiSampai: String,
        ongkir: Long
    ): LoadState<String>

    suspend fun checkOngkir(keranjangIds: List<String>): LoadState<List<Courier>>
    suspend fun getActiveOrders(): LoadState<List<Order>>
    suspend fun getDoneOrders(): LoadState<List<Order>>
    suspend fun getOrderDetail(headerId: String): LoadState<OrderDetail>
    suspend fun payOrder(pesananHeaderId: String): LoadState<Any>
    suspend fun finishOrder(pesananHeaderId: String): LoadState<Any>
}