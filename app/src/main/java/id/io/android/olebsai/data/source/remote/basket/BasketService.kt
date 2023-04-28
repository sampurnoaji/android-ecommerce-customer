package id.io.android.olebsai.data.source.remote.basket

import id.io.android.olebsai.data.model.request.basket.AddProductToBasketRequest
import id.io.android.olebsai.data.model.request.basket.RemoveProductRequest
import id.io.android.olebsai.data.model.request.basket.UpdateNoteRequest
import id.io.android.olebsai.data.model.request.basket.UpdateQtyRequest
import id.io.android.olebsai.data.model.response.BaseResponse
import id.io.android.olebsai.data.model.response.basket.BasketResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BasketService {

    companion object {
        const val BASKET = "/v1/keranjang"
    }

    @POST("$BASKET/create")
    suspend fun addProductToBasket(@Body request: AddProductToBasketRequest): BaseResponse<Any>

    @GET("$BASKET/get-by-user")
    suspend fun getBasket(): BaseResponse<List<BasketResponse>>

    @POST("$BASKET/edit-qty")
    suspend fun updateQty(@Body request: UpdateQtyRequest): BaseResponse<Any>

    @POST("$BASKET/edit-catatan")
    suspend fun updateNote(@Body request: UpdateNoteRequest): BaseResponse<Any>

    @POST("$BASKET/delete")
    suspend fun removeProduct(@Body request: RemoveProductRequest): BaseResponse<Any>
}