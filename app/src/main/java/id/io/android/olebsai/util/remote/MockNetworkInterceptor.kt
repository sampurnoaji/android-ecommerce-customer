package id.io.android.olebsai.util.remote

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

class MockNetworkInterceptor @Inject constructor(@ApplicationContext val context: Context) :
    Interceptor {

    companion object {
        private val JSON_MEDIA_TYPE = "application/json".toMediaTypeOrNull()
        private const val MOCK = "mock"
        private const val SUCCESS_RESPONSE_CODE = 200
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        val request = chain.request()
        val header = request.header(MOCK)

        if (header != null) {
            val fileName = request.url.pathSegments.last()
            return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .message("")
                .code(SUCCESS_RESPONSE_CODE)
                .body(
                    context.readFileFromAssets("mock/$fileName.json").toResponseBody(
                        JSON_MEDIA_TYPE
                    )
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