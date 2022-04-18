package id.io.android.seller.core

interface BaseUseCase<Params, out T> {
    suspend operator fun invoke(params: Params): T
}