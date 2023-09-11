package id.io.android.olebsai.data.source.remote.user

import id.io.android.olebsai.data.model.request.user.login.LoginRequest
import id.io.android.olebsai.data.model.request.user.login.LoginWithOtpRequest
import id.io.android.olebsai.data.model.response.user.DistrictResponse
import id.io.android.olebsai.data.model.response.user.FrontPageDataResponse
import id.io.android.olebsai.data.model.response.user.LoginResponse
import id.io.android.olebsai.data.model.response.user.ProvinceResponse
import id.io.android.olebsai.data.model.response.user.SubDistrictResponse
import id.io.android.olebsai.data.model.response.user.UserResponse
import id.io.android.olebsai.domain.model.user.LoginParams
import id.io.android.olebsai.domain.model.user.RegisterParams
import id.io.android.olebsai.util.remote.ResponseHelper
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(private val service: UserService) :
    ResponseHelper() {

    suspend fun register(params: RegisterParams): String {
        return call {
            service.register(params.toRequest()).message.orEmpty()
        }
    }

    suspend fun login(params: LoginParams): LoginResponse {
        return call {
            service.login(LoginRequest(email = params.email, password = params.password!!)).data
        }
    }

    suspend fun loginWithOtp(params: LoginParams): String {
        return call {
            service.loginWithOtp(LoginWithOtpRequest(email = params.email, otp = params.otp!!)).data
        }
    }

    suspend fun updateProfile(params: RegisterParams) {
        return call { service.updateProfile(params.toUpdateProfileRequest()) }
    }

    suspend fun getUser(): UserResponse = call { service.getUser().data }

    suspend fun getFrontPageData(): FrontPageDataResponse =
        call { service.getFrontPageData().data }

    suspend fun getProvinces(): List<ProvinceResponse> {
        return call { service.getProvinces().data }
    }

    suspend fun getDistricts(idPropinsi: String): List<DistrictResponse> {
        return call { service.getDistricts(idPropinsi).data }
    }

    suspend fun getSubDistricts(idKota: String): List<SubDistrictResponse> {
        return call { service.getSubDistricts(idKota).data }
    }
}