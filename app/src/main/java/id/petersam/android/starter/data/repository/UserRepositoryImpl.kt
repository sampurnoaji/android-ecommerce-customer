package id.petersam.android.starter.data.repository

import id.petersam.android.starter.data.source.remote.UserRemoteDataSource
import id.petersam.android.starter.domain.model.User
import id.petersam.android.starter.domain.repository.UserRepository
import id.petersam.android.starter.util.LoadState
import id.petersam.android.starter.util.remote.ResponseHelper
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource
) : UserRepository, ResponseHelper() {

    override suspend fun getUser(): LoadState<User?> {
        return map { remoteDataSource.getUser()?.toDomain() }
    }
}