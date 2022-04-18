package id.petersam.android.starter.domain.usecase

import javax.inject.Inject

data class UserUseCases @Inject constructor(
    val getUserUseCase: GetUserUseCase
)
