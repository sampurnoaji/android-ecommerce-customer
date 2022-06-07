package id.petersam.android.starter.data.source.remote

import id.petersam.android.starter.data.model.response.UserResponse
import id.petersam.android.starter.util.LoadState
import id.petersam.android.starter.util.remote.ResponseHelper
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(private val api: ApiService): ResponseHelper() {
    suspend fun getUser(id: Int): LoadState<UserResponse> = call { api.getUser(id) }
}