package id.io.android.seller.domain.usecase

import javax.inject.Inject

data class UserUseCases @Inject constructor(
    val getUserUseCase: GetUserUseCase
)
