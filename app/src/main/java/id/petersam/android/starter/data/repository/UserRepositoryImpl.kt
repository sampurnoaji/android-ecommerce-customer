package id.petersam.android.starter.data.repository

import id.petersam.android.starter.data.source.local.UserLocalDataSource
import id.petersam.android.starter.data.source.remote.UserRemoteDataSource
import id.petersam.android.starter.domain.model.User
import id.petersam.android.starter.domain.repository.UserRepository
import id.petersam.android.starter.util.LoadState
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localDataSource: UserLocalDataSource,
    private val remoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun getUser(id: Int): LoadState<User> {
        return when (val result = remoteDataSource.getUser(id)) {
            is LoadState.Success -> LoadState.Success(result.data?.toDomain())
            is LoadState.Error -> LoadState.Error(result.code, result.message)
            else -> LoadState.Error()
        }
    }
}