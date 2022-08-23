package id.io.android.olebsai.core

interface BaseUseCase<Params, out T> {
    suspend operator fun invoke(params: Params): T
}