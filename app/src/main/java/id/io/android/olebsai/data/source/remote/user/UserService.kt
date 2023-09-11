package id.io.android.olebsai.data.source.remote.user

import id.io.android.olebsai.data.model.request.user.UpdateProfileRequest
import id.io.android.olebsai.data.model.request.user.login.LoginRequest
import id.io.android.olebsai.data.model.request.user.login.LoginWithOtpRequest
import id.io.android.olebsai.data.model.request.user.register.RegisterRequest
import id.io.android.olebsai.data.model.response.BaseResponse
import id.io.android.olebsai.data.model.response.user.DistrictResponse
import id.io.android.olebsai.data.model.response.user.FrontPageDataResponse
import id.io.android.olebsai.data.model.response.user.LoginResponse
import id.io.android.olebsai.data.model.response.user.ProvinceResponse
import id.io.android.olebsai.data.model.response.user.SubDistrictResponse
import id.io.android.olebsai.data.model.response.user.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    companion object {
        const val REGISTER = "/v1/user/register/buyer"
        const val LOGIN = "login"
        const val LOGIN_WITH_OTP = "login/otp"
        const val FRONT_PAGE = "v1/front-page"
    }

    @GET("/v1/user/get-by-token")
    suspend fun getUser(): BaseResponse<UserResponse>

    @POST(REGISTER)
    suspend fun register(@Body request: RegisterRequest): BaseResponse<String>

    @POST(LOGIN)
    suspend fun login(@Body request: LoginRequest): BaseResponse<LoginResponse>

    @POST(LOGIN_WITH_OTP)
    suspend fun loginWithOtp(@Body request: LoginWithOtpRequest): BaseResponse<String>

    @POST("/v1/user/update/buyer")
    suspend fun updateProfile(@Body request: UpdateProfileRequest): BaseResponse<Any>

    @GET("$FRONT_PAGE/get-front-page")
    suspend fun getFrontPageData(): BaseResponse<FrontPageDataResponse>

    @GET("v1/raja-ongkir/get-propinsi")
    suspend fun getProvinces(): BaseResponse<List<ProvinceResponse>>

    @GET("v1/raja-ongkir/get-kota/{idPropinsi}")
    suspend fun getDistricts(@Path("idPropinsi") idPropinsi: String): BaseResponse<List<DistrictResponse>>

    @GET("v1/raja-ongkir/get-kecamatan/{idKota}")
    suspend fun getSubDistricts(@Path("idKota") idKota: String): BaseResponse<List<SubDistrictResponse>>
}