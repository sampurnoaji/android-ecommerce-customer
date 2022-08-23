package id.petersam.android.starter.data.source.remote

import id.petersam.android.starter.data.model.response.UserResponse
import id.petersam.android.starter.util.remote.ResponseHelper
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(private val api: ApiService): ResponseHelper() {
    suspend fun getUser(): UserResponse? = call { api.getUser().data }
}