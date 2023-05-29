package id.io.android.olebsai.data.repository

import id.io.android.olebsai.data.source.local.user.UserLocalDataSource
import id.io.android.olebsai.data.source.remote.user.UserRemoteDataSource
import id.io.android.olebsai.domain.model.address.Address
import id.io.android.olebsai.domain.model.user.FrontPageData
import id.io.android.olebsai.domain.model.user.LoginParams
import id.io.android.olebsai.domain.model.user.RegisterParams
import id.io.android.olebsai.domain.model.user.User
import id.io.android.olebsai.domain.repository.UserRepository
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.remote.ResponseHelper
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl @Inject constructor(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource,
) : UserRepository, ResponseHelper() {

    override suspend fun register(registerParams: RegisterParams): LoadState<String> {
        return map { remoteDataSource.register(registerParams) }
    }

    override suspend fun login(loginParams: LoginParams): LoadState<Pair<User, String>> {
        return map {
            val result = remoteDataSource.login(loginParams)
            Pair(result.toDomain(), result.token.orEmpty())
        }
    }

    override suspend fun loginWithOtp(loginParams: LoginParams): LoadState<String> {
        return map { remoteDataSource.loginWithOtp(loginParams) }
    }

    override suspend fun getFrontPageData(): LoadState<FrontPageData> {
        return map { remoteDataSource.getFrontPageData().toDomain() }
    }

    override suspend fun getMasterUser(): LoadState<User> {
        return map { remoteDataSource.getUser().toDomain() }
    }

    override fun getAddressList(): Flow<List<Address>> {
        return localDataSource.getAddressList().map {
            it.map { addressEntity -> addressEntity.toDomain() }
        }
    }

    override suspend fun addAddress(address: Address) {
        localDataSource.addAddress(address.toEntity())
    }

    override suspend fun updateAddress(address: Address) {
        localDataSource.updateAddress(address.toEntity())
    }

    override suspend fun deleteAddress(addressId: Int) {
        localDataSource.deleteAddress(addressId)
    }

    override suspend fun setDefaultAddress(address: Address) {
        localDataSource.setDefaultAddress(address.toEntity())
    }

    override suspend fun getDefaultAddress(): Address? {
        return localDataSource.getDefaultAddress()?.toDomain()
    }

    override fun isLoggedIn(): Boolean = localDataSource.isLoggedIn()
    override fun setLoggedIn(isLoggedIn: Boolean) {
        localDataSource.setLoggedIn(isLoggedIn)
    }

    override fun setToken(token: String) {
        localDataSource.setToken(token)
    }

    override fun getToken(): String = localDataSource.getToken()

    override fun saveUser(user: User) {
        localDataSource.saveUser(user)
    }

    override fun getUser(): User? = localDataSource.getUser()
}