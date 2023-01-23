package id.io.android.olebsai.util.remote

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

/**
 * Usage:
 * 1. Service class -> @Headers("mock:{statusCode}")
 * 2. Create {lastPathName}.json response class on assets folder
 */
class MockNetworkInterceptor @Inject constructor(@ApplicationContext val context: Context) :
    Interceptor {

    companion object {
        private val JSON_MEDIA_TYPE = "application/json".toMediaTypeOrNull()
        private const val MOCK = "mock"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        val request = chain.request()
        val header = request.header(MOCK)

        if (header != null) {
            val code = header.toIntOrNull() ?: 0
            val fileName = request.url.pathSegments.last()
            return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .message("")
                .code(code)
                .body(
                    context.readFileFromAssets(
                        if (code == 200) "mock/$fileName.json"
                        else "mock/$code.json"
                    ).toResponseBody(JSON_MEDIA_TYPE)
                )
                .build()
        }

        val newRequest = requestBuilder.removeHeader(MOCK).build()
        return chain.proceed(newRequest)
    }

    private fun Context.readFileFromAssets(filePath: String): String {
        return resources.assets.open(filePath).bufferedReader().use {
            it.readText()
        }
    }
}