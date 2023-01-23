package id.io.android.olebsai.data.source.remote.address

import id.io.android.olebsai.data.model.request.address.AddAddressRequest
import id.io.android.olebsai.data.model.response.BaseResponse
import id.io.android.olebsai.data.model.response.address.AddressListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface AddressService {
    @Headers("mock:200")
    @GET("address-list")
    suspend fun getAddressList(): BaseResponse<AddressListResponse>

    @Headers("mock:200")
    @POST("any")
    suspend fun setAddressDefault(@Query("id") addressId: Int): BaseResponse<Any>

    @Headers("mock:200")
    @POST("any")
    suspend fun addAddress(@Body request: AddAddressRequest): BaseResponse<Any>

    @Headers("mock:200")
    @POST("any")
    suspend fun updateAddress(
        @Body request: AddAddressRequest,
        @Query("id") addressId: Int
    ): BaseResponse<Any>

    @Headers("mock:200")
    @POST("any")
    suspend fun deleteAddress(@Query("id") addressId: Int): BaseResponse<Any>
}