package id.io.android.seller.data.source.remote

import id.io.android.seller.data.model.response.UserResponse
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(private val api: ApiService) {
    suspend fun getUser(id: Int): UserResponse = api.getUser(id)
}