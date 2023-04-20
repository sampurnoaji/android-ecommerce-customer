package id.io.android.olebsai.data.source.remote.user

import id.io.android.olebsai.data.model.request.user.login.LoginRequest
import id.io.android.olebsai.data.model.request.user.login.LoginWithOtpRequest
import id.io.android.olebsai.data.model.request.user.register.RegisterRequest
import id.io.android.olebsai.data.model.response.BaseResponse
import id.io.android.olebsai.data.model.response.user.FrontPageDataResponse
import id.io.android.olebsai.data.model.response.user.UserResponse
import id.io.android.olebsai.data.model.response.user.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserService {
    companion object {
        const val REGISTER = "/v1/user/register/buyer"
        const val LOGIN = "login"
        const val LOGIN_WITH_OTP = "login/otp"
        const val FRONT_PAGE = "v1/front-page"
    }

    @Headers("mock:true")
    @GET("user")
    suspend fun getUser(): BaseResponse<UserResponse>

    @POST(REGISTER)
    suspend fun register(@Body request: RegisterRequest): BaseResponse<String>

    @POST(LOGIN)
    suspend fun login(@Body request: LoginRequest): BaseResponse<LoginResponse>

    @POST(LOGIN_WITH_OTP)
    suspend fun loginWithOtp(@Body request: LoginWithOtpRequest): BaseResponse<String>

    @GET("$FRONT_PAGE/get-front-page")
    suspend fun getFrontPageData(): BaseResponse<FrontPageDataResponse>
}