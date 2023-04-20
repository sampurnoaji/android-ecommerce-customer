package id.io.android.olebsai.data.source.remote.user

import id.io.android.olebsai.data.model.request.user.login.LoginRequest
import id.io.android.olebsai.data.model.request.user.login.LoginWithOtpRequest
import id.io.android.olebsai.data.model.request.user.register.RegisterRequest
import id.io.android.olebsai.data.model.response.user.FrontPageDataResponse
import id.io.android.olebsai.data.model.response.user.UserResponse
import id.io.android.olebsai.data.model.response.user.LoginResponse
import id.io.android.olebsai.domain.model.user.LoginParams
import id.io.android.olebsai.domain.model.user.RegisterParams
import id.io.android.olebsai.util.remote.ResponseHelper
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(private val service: UserService) :
    ResponseHelper() {

    suspend fun register(params: RegisterParams): String {
        return call {
            service.register(
                RegisterRequest(
                    email = params.email,
                    namaUser = params.name,
                    nomorHp = params.phoneNumber,
                    password = params.password
                )
            ).message.orEmpty()
        }
    }

    suspend fun login(params: LoginParams): LoginResponse {
        return call {
            service.login(LoginRequest(email = params.email, password = params.password!!)).data!!
        }
    }

    suspend fun loginWithOtp(params: LoginParams): String {
        return call {
            service.loginWithOtp(LoginWithOtpRequest(email = params.email, otp = params.otp!!)).data!!
        }
    }

    suspend fun getUser(): UserResponse = call { service.getUser().data!! }

    suspend fun getFrontPageData(): FrontPageDataResponse =
        call { service.getFrontPageData().data!! }
}