package id.io.android.olebsai.data.repository

import id.io.android.olebsai.data.source.remote.UserRemoteDataSource
import id.io.android.olebsai.domain.model.User
import id.io.android.olebsai.domain.repository.UserRepository
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.remote.ResponseHelper
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource
) : UserRepository, ResponseHelper() {

    override suspend fun getUser(): LoadState<User?> {
        return map { remoteDataSource.getUser()?.toDomain() }
    }
}