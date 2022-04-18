package id.io.android.seller.data.repository

import id.io.android.seller.data.source.local.UserLocalDataSource
import id.io.android.seller.data.source.remote.UserRemoteDataSource
import id.io.android.seller.domain.repository.UserRepository
import id.io.android.seller.domain.model.User
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun getUser(id: Int): User {
        return localDataSource.getUser()?.toDomain() ?: remoteDataSource.getUser(id).toDomain()
    }
}