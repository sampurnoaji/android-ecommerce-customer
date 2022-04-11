package id.io.android.seller.domain.usecase

import id.io.android.seller.domain.model.User
import id.io.android.seller.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: UserRepository) {
    suspend operator fun invoke(): User? = repository.getUser()
}