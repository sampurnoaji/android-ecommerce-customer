package id.io.android.olebsai.util

sealed class LoadState<out T> {
    object Loading: LoadState<Nothing>()
    data class Success<out T>(val data: T?) : LoadState<T>()
    data class Error(
        val code: Int? = null,
        val message: String? = null,
    ) : LoadState<Nothing>()
}
