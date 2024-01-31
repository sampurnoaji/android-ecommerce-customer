package id.io.android.olebsai.data.source.remote.basket

import id.io.android.olebsai.data.model.request.basket.AddProductToBasketRequest
import id.io.android.olebsai.data.model.request.basket.CheckOngkirRequest
import id.io.android.olebsai.data.model.request.basket.CheckoutRequest
import id.io.android.olebsai.data.model.request.basket.OrderRequest
import id.io.android.olebsai.data.model.request.basket.RemoveProductRequest
import id.io.android.olebsai.data.model.request.basket.UpdateNoteRequest
import id.io.android.olebsai.data.model.request.basket.UpdateQtyRequest
import id.io.android.olebsai.data.model.response.BaseResponse
import id.io.android.olebsai.data.model.response.basket.ActiveOrderResponse
import id.io.android.olebsai.data.model.response.basket.BasketResponse
import id.io.android.olebsai.data.model.response.basket.CouriersResponse
import id.io.android.olebsai.data.model.response.basket.CouriersResponse.CourierResponse
import id.io.android.olebsai.data.model.response.basket.OrderDetailResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("v1/master-kurir/get-all")
    suspend fun getCouriers(): BaseResponse<CouriersResponse>

    @POST("/v1/pesanan/checkout")
    suspend fun checkout(@Body request: CheckoutRequest): BaseResponse<String>

    @POST("v1/pesanan/cek-ongkir")
    suspend fun checkOngkir(@Body request: CheckOngkirRequest): BaseResponse<List<CourierResponse>>

    @GET("v1/pesanan/get-by-buyer")
    suspend fun getActiveOrders(): BaseResponse<ActiveOrderResponse>

    @GET("v1/pesanan/get-histori-pesanan-buyer")
    suspend fun getDoneOrders(
        @Query("pageNumber") page: Int,
        @Query("pageSize") size: Int,
    ): BaseResponse<ActiveOrderResponse>

    @GET("/v1/pesanan/get-list-pesanan-by-header/{headerId}")
    suspend fun getOrderDetail(
        @Path("headerId") headerId: String
    ): BaseResponse<OrderDetailResponse>

    @POST("/v1/pesanan/bayar")
    suspend fun payOrder(@Body request: OrderRequest): BaseResponse<Any>

    @POST("/v1/pesanan/selesai")
    suspend fun finishOrder(@Body request: OrderRequest): BaseResponse<Any>
}