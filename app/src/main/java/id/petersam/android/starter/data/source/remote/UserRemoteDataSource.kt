package id.petersam.android.starter.data.source.remote

import id.petersam.android.starter.data.model.response.UserResponse
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(private val api: ApiService) {
    suspend fun getUser(id: Int): UserResponse = api.getUser(id)
}