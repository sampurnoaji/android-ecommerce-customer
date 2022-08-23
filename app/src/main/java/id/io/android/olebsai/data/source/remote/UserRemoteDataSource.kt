package id.io.android.olebsai.data.source.remote

import id.io.android.olebsai.data.model.response.UserResponse
import id.io.android.olebsai.util.remote.ResponseHelper
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(private val api: ApiService): ResponseHelper() {
    suspend fun getUser(): UserResponse? = call { api.getUser().data }
}