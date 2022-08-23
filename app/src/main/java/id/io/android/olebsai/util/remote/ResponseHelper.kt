package id.io.android.olebsai.util.remote

import id.io.android.olebsai.util.LoadState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException

open class ResponseHelper {

    suspend fun <T> call(api: suspend () -> T): T {
        return withContext(Dispatchers.IO) {
            try {
                api.invoke()
            } catch (e: Exception) {
                throw e
            }
        }
    }

    suspend fun <T> map(api: suspend () -> T): LoadState<T> {
        return try {
            LoadState.Success(api.invoke())
        } catch (e: Exception) {
            e.mapError()
        }
    }

    private fun Exception.mapError(): LoadState.Error {
        return when (this) {
            is HttpException -> {
                parseErrorBody()
            }
            else -> {
                LoadState.Error(message = message)
            }
        }
    }

    private fun HttpException.parseErrorBody(): LoadState.Error {
        return try {
            val errorResponse = response()?.errorBody()?.string()
            val errorMessage = errorResponse?.getOrNull(JSON_KEY_MESSAGE)
            LoadState.Error(code = code(), message = errorMessage)
        } catch (e: Exception) {
            LoadState.Error(code = code(), message = message())
        }
    }

    private fun String.getOrNull(key: String): String? {
        return try {
            val json = JSONObject(this)
            json.getString(key)
        } catch (e: JSONException) {
            null
        }
    }

    companion object {
        private const val JSON_KEY_MESSAGE = "message"
    }
}