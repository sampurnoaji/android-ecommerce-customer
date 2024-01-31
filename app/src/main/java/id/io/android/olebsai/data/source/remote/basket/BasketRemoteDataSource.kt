package id.io.android.olebsai.data.source.remote.basket

import id.io.android.olebsai.data.model.request.basket.AddProductToBasketRequest
import id.io.android.olebsai.data.model.request.basket.CheckOngkirRequest
import id.io.android.olebsai.data.model.request.basket.CheckoutRequest
import id.io.android.olebsai.data.model.request.basket.OrderRequest
import id.io.android.olebsai.data.model.request.basket.RemoveProductRequest
import id.io.android.olebsai.data.model.request.basket.UpdateNoteRequest
import id.io.android.olebsai.data.model.request.basket.UpdateQtyRequest
import id.io.android.olebsai.data.model.response.basket.BasketResponse
import id.io.android.olebsai.data.model.response.basket.CouriersResponse.CourierResponse
import id.io.android.olebsai.data.model.response.basket.OrderDetailResponse
import id.io.android.olebsai.data.model.response.basket.OrderResponse
import id.io.android.olebsai.util.remote.ResponseHelper
import javax.inject.Inject

class BasketRemoteDataSource @Inject constructor(private val service: BasketService) :
    ResponseHelper() {

    suspend fun addProductToBasket(
        productId: String,
        qty: Int,
        tokoId: String,
        catatan: String
    ): String {
        return call {
            service.addProductToBasket(
                AddProductToBasketRequest(
                    produkId = productId,
                    qty = qty,
                    tokoId = tokoId,
                    catatan = catatan
                )
            ).message.orEmpty()
        }
    }

    suspend fun getBasket(): List<BasketResponse> {
        return call { service.getBasket().data }
    }

    suspend fun updateQty(basketId: String, qty: Int): String {
        return call { service.updateQty(UpdateQtyRequest(basketId, qty)).message.orEmpty() }
    }

    suspend fun updateNote(basketId: String, note: String): String {
        return call { service.updateNote(UpdateNoteRequest(basketId, note)).message.orEmpty() }
    }

    suspend fun removeProduct(productId: String): String {
        return call { service.removeProduct(RemoveProductRequest(productId)).message.orEmpty() }
    }

    suspend fun getCouriers(): List<CourierResponse> {
        return call { service.getCouriers().data.data }
    }

    suspend fun checkout(
        basketIds: List<String>,
        namaJasaPengiriman: String,
        servisJasaPengiriman: String,
        estimasiSampai: String,
        ongkir: Long
    ): String {
        return call {
            service.checkout(
                CheckoutRequest(
                    basketIds,
                    namaJasaPengiriman = namaJasaPengiriman,
                    ongkir = ongkir,
                    servisJasaPengiriman = servisJasaPengiriman,
                    estimasiSampai = estimasiSampai,
                )
            ).message.orEmpty()
        }
    }

    suspend fun checkOngkir(keranjangIds: List<String>): List<CourierResponse> {
        return call { service.checkOngkir(CheckOngkirRequest(keranjangIds)).data }
    }

    suspend fun getActiveOrders(): List<OrderResponse> {
        return call { service.getActiveOrders().data.data }
    }

    suspend fun getDoneOrders(
        page: Int,
        size: Int,
    ): List<OrderResponse> {
        return call {
            service.getDoneOrders(
                page = page,
                size = size,
            ).data.data
        }
    }

    suspend fun getOrderDetail(headerId: String): OrderDetailResponse {
        return call { service.getOrderDetail(headerId).data }
    }

    suspend fun payOrder(pesananHeaderId: String): Any {
        return call { service.payOrder(OrderRequest(pesananHeaderId)) }
    }

    suspend fun finishOrder(pesananHeaderId: String): Any {
        return call { service.finishOrder(OrderRequest(pesananHeaderId)) }
    }
}