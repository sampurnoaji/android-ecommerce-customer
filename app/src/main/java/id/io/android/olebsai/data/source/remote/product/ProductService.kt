package id.io.android.olebsai.data.source.remote.product

import id.io.android.olebsai.data.model.response.BaseResponse
import id.io.android.olebsai.data.model.response.product.CategoriesResponse
import id.io.android.olebsai.data.model.response.product.ProductListResponse
import id.io.android.olebsai.data.model.response.product.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
    companion object {
        const val PRODUCTS = "/v1/produk"
    }

    @GET("$PRODUCTS/get-all")
    suspend fun getProductList(
        @Query("pageNumber") page: Int,
        @Query("pageSize") size: Int,
        @Query("namaProduk") namaProduk: String,
        @Query("kategoriId") kategoriId: String?,
    ): BaseResponse<ProductListResponse>

    @GET("$PRODUCTS/get-by-id/{id}")
    suspend fun getProductDetail(@Path("id") productId: String): BaseResponse<ProductResponse>

    @GET("/v1/master-kategori/get-all")
    suspend fun getCategories(): BaseResponse<CategoriesResponse>
}