package id.io.android.olebsai.domain.repository

import id.io.android.olebsai.domain.model.user.FrontPageData
import id.io.android.olebsai.domain.model.user.LoginParams
import id.io.android.olebsai.domain.model.user.RegisterParams
import id.io.android.olebsai.domain.model.user.User
import id.io.android.olebsai.util.LoadState

interface UserRepository {
    suspend fun register(registerParams: RegisterParams): LoadState<String>
    suspend fun login(loginParams: LoginParams): LoadState<Pair<User, String>>
    suspend fun loginWithOtp(loginParams: LoginParams): LoadState<String>
    suspend fun getFrontPageData(): LoadState<FrontPageData>

    fun isLoggedIn(): Boolean
    fun setLoggedIn(isLoggedIn: Boolean)
    fun setToken(token: String)
    fun getToken(): String
    fun saveUser(user: User)
    fun getUser(): User?
}