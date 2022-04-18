package id.petersam.android.starter.data.source.remote

import id.petersam.android.starter.data.model.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {
    @Headers("mock:true")
    @GET("/api/user/{id}")
    suspend fun getUser(@Path("id") id: Int): UserResponse
}