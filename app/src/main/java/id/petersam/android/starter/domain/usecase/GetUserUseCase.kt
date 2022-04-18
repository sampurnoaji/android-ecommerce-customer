package id.petersam.android.starter.domain.usecase

import id.petersam.android.starter.core.BaseUseCase
import id.petersam.android.starter.domain.model.User
import id.petersam.android.starter.domain.repository.UserRepository
import id.petersam.android.starter.util.NoParams
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: UserRepository): BaseUseCase<NoParams, User> {
    override suspend fun invoke(params: NoParams): User = repository.getUser(2)
}