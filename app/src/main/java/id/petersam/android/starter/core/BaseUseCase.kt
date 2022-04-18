package id.petersam.android.starter.core

interface BaseUseCase<Params, out T> {
    suspend operator fun invoke(params: Params): T
}