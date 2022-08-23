package id.io.android.olebsai.data.source.remote

import id.io.android.olebsai.data.model.response.BaseResponse
import id.io.android.olebsai.data.model.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {
    companion object {
        const val USER = "/user"
    }
    @Headers("mock:true")
    @GET(USER)
    suspend fun getUser(): BaseResponse<UserResponse>
}