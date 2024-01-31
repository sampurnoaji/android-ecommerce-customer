package id.io.android.olebsai.data.repository

import id.io.android.olebsai.data.source.remote.basket.BasketRemoteDataSource
import id.io.android.olebsai.data.source.remote.basket.OrderPagingSource
import id.io.android.olebsai.domain.model.basket.BasketItem
import id.io.android.olebsai.domain.model.basket.Courier
import id.io.android.olebsai.domain.model.order.Order
import id.io.android.olebsai.domain.model.order.OrderDetail
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
        return map {
            remoteDataSource.addProductToBasket(
                productId = productId,
                qty = qty,
                tokoId = tokoId,
                catatan = catatan
            )
        }
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

    override suspend fun getCouriers(): LoadState<List<Courier>> {
        return map { remoteDataSource.getCouriers().map { it.toDomain() } }
    }

    override suspend fun checkout(
        basketIds: List<String>,
        namaJasaPengiriman: String,
        servisJasaPengiriman: String,
        estimasiSampai: String,
        ongkir: Long
    ): LoadState<String> {
        return map {
            remoteDataSource.checkout(
                basketIds,
                namaJasaPengiriman,
                servisJasaPengiriman,
                estimasiSampai,
                ongkir
            )
        }
    }

    override suspend fun checkOngkir(keranjangIds: List<String>): LoadState<List<Courier>> {
        return map { remoteDataSource.checkOngkir(keranjangIds).map { it.toDomain() } }
    }

    override suspend fun getActiveOrders(): LoadState<List<Order>> {
        return map { remoteDataSource.getActiveOrders().map { it.toDomain() } }
    }

    override fun getOrderPagingSource(): OrderPagingSource {
        return OrderPagingSource(remoteDataSource)
    }

    override suspend fun getOrderDetail(headerId: String): LoadState<OrderDetail> {
        return map { remoteDataSource.getOrderDetail(headerId).toDomain() }
    }

    override suspend fun payOrder(pesananHeaderId: String): LoadState<Any> {
        return map { remoteDataSource.payOrder(pesananHeaderId) }
    }

    override suspend fun finishOrder(pesananHeaderId: String): LoadState<Any> {
        return map { remoteDataSource.finishOrder(pesananHeaderId) }
    }
}