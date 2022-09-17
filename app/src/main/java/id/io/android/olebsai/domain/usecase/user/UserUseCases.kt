package id.io.android.olebsai.domain.usecase.user

import javax.inject.Inject

data class UserUseCases @Inject constructor(
    val checkLoggedInUseCase: CheckLoggedInUseCase,
    val setLoggedInUseCase: SetLoggedInUseCase
)
