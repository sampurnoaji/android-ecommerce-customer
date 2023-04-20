package id.io.android.olebsai.data.source.remote.product

import id.io.android.olebsai.data.model.response.product.ProductListResponse
import id.io.android.olebsai.data.model.response.product.ProductResponse
import id.io.android.olebsai.util.remote.ResponseHelper
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(private val api: ProductService) :
    ResponseHelper() {

    suspend fun getProductList(page: Int, size: Int, namaProduk: String): ProductListResponse =
        call { api.getProductList(page, size, namaProduk).data!! }

    suspend fun getProductDetail(id: String): ProductResponse =
        call { api.getProductDetail(id).data!! }
}