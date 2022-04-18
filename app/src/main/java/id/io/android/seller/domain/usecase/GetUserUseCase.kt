package id.io.android.seller.domain.usecase

import id.io.android.seller.core.BaseUseCase
import id.io.android.seller.domain.model.User
import id.io.android.seller.domain.repository.UserRepository
import id.io.android.seller.util.NoParams
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: UserRepository): BaseUseCase<NoParams, User> {
    override suspend fun invoke(params: NoParams): User = repository.getUser(2)
}