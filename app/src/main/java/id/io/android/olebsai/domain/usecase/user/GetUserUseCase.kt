package id.io.android.olebsai.domain.usecase.user

import id.io.android.olebsai.core.BaseUseCase
import id.io.android.olebsai.domain.model.User
import id.io.android.olebsai.domain.repository.UserRepository
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.NoParams
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: UserRepository) :
    BaseUseCase<NoParams, LoadState<User?>> {

    override suspend fun invoke(params: NoParams): LoadState<User?> {
        return repository.getUser()
    }
}