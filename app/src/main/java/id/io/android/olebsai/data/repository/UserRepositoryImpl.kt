package id.io.android.olebsai.data.repository

import id.io.android.olebsai.data.source.local.user.UserLocalDataSource
import id.io.android.olebsai.data.source.remote.user.UserRemoteDataSource
import id.io.android.olebsai.domain.model.user.FrontPageData
import id.io.android.olebsai.domain.model.user.LoginParams
import id.io.android.olebsai.domain.model.user.RegisterParams
import id.io.android.olebsai.domain.model.user.User
import id.io.android.olebsai.domain.repository.UserRepository
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.remote.ResponseHelper
import javax.inject.Inject

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