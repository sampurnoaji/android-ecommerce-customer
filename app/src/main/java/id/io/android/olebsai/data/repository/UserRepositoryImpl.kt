package id.io.android.olebsai.data.repository

import id.io.android.olebsai.data.source.local.user.UserLocalDataSource
import id.io.android.olebsai.domain.repository.UserRepository
import id.io.android.olebsai.util.remote.ResponseHelper
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localDataSource: UserLocalDataSource
) : UserRepository, ResponseHelper() {

    override fun isLoggedIn(): Boolean = localDataSource.isLoggedIn()
    override fun setLoggedIn(isLoggedIn: Boolean) {
        localDataSource.setLoggedIn(isLoggedIn)
    }
}