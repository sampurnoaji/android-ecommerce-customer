package id.petersam.android.starter.data.repository

import id.petersam.android.starter.data.source.local.UserLocalDataSource
import id.petersam.android.starter.data.source.remote.UserRemoteDataSource
import id.petersam.android.starter.domain.repository.UserRepository
import id.petersam.android.starter.domain.model.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun getUser(id: Int): User {
        return localDataSource.getUser()?.toDomain() ?: remoteDataSource.getUser(id).toDomain()
    }
}