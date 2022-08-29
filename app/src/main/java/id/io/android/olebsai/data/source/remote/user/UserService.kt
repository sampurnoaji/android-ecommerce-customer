package id.io.android.olebsai.data.source.remote.user

import id.io.android.olebsai.data.model.response.BaseResponse
import id.io.android.olebsai.data.model.response.user.UserResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface UserService {
    companion object {
        const val USER = "/user"
    }

    @Headers("mock:true")
    @GET(USER)
    suspend fun getUser(): BaseResponse<UserResponse>
}