package id.io.android.olebsai.data.source.remote.user

import id.io.android.olebsai.data.model.response.user.UserResponse
import id.io.android.olebsai.util.remote.ResponseHelper
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(private val api: UserService): ResponseHelper() {
    suspend fun getUser(): UserResponse = call { api.getUser().data }
}