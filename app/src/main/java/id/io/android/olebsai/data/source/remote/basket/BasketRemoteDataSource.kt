package id.io.android.olebsai.data.source.remote.basket

import id.io.android.olebsai.data.model.request.basket.AddProductToBasketRequest
import id.io.android.olebsai.data.model.request.basket.CheckoutRequest
import id.io.android.olebsai.data.model.request.basket.RemoveProductRequest
import id.io.android.olebsai.data.model.request.basket.UpdateNoteRequest
import id.io.android.olebsai.data.model.request.basket.UpdateQtyRequest
import id.io.android.olebsai.data.model.response.basket.BasketResponse
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
        return call { service.getBasket().data!! }
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

    suspend fun checkout(basketIds: List<String>, namaJasaPengiriman: String): String {
        return call {
            service.checkout(CheckoutRequest(basketIds, namaJasaPengiriman)).message.orEmpty()
        }
    }
}