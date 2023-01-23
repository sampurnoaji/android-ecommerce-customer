package id.io.android.olebsai.data.source.remote.product

import id.io.android.olebsai.data.model.response.product.ProductDetailResponse
import id.io.android.olebsai.data.model.response.product.ProductListResponse
import id.io.android.olebsai.util.remote.ResponseHelper
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(private val api: ProductService) :
    ResponseHelper() {

    suspend fun getProductList(page: Int): ProductListResponse =
        call { api.getProductList(page).data }

    suspend fun getProductDetail(id: Int): ProductDetailResponse =
        call { api.getProductDetail(id).data }
}