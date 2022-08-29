package id.io.android.olebsai.data.source.remote.product

import id.io.android.olebsai.data.model.response.BaseResponse
import id.io.android.olebsai.data.model.response.product.ProductListResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ProductService {
    companion object {
        const val PRODUCTS = "/products"
    }

    @Headers("mock:true")
    @GET(PRODUCTS)
    suspend fun getProductList(@Query("page") page: Int): BaseResponse<ProductListResponse>
}