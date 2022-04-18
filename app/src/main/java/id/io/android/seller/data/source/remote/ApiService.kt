package id.io.android.seller.data.source.remote

import id.io.android.seller.data.model.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {
    @Headers("mock:true")
    @GET("/api/user/{id}")
    suspend fun getUser(@Path("id") id: Int): UserResponse
}