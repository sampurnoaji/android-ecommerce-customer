package id.io.android.olebsai.domain.usecase

import javax.inject.Inject

data class UserUseCases @Inject constructor(
    val getUserUseCase: GetUserUseCase
)
