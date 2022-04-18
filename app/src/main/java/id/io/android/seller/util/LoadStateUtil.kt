package id.io.android.seller.util

import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException

object LoadStateUtil {
    private const val JSON_KEY_MESSAGE = "message"

    fun <T> map(data: T): LoadState<T> {
        return try {
            LoadState.Success(data)
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
}