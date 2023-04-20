package id.io.android.olebsai.di.interceptor

import android.content.SharedPreferences
import id.io.android.olebsai.data.source.remote.user.UserService
import id.io.android.olebsai.di.SharedPrefModule
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor @Inject constructor(private val preferences: SharedPreferences) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url.toString()
        val requestBuilder = chain.request().newBuilder().apply {
            addBearerAuthorization()
        }

        if (url.isNoNeedBearerAuthorization()) {
            requestBuilder.removeBearerAuthorization()
        }

        val newRequest = requestBuilder.build()
        return chain.proceed(newRequest)
    }

    private fun String.isNoNeedBearerAuthorization(): Boolean {
        val urls = listOf(
            UserService.REGISTER,
            UserService.LOGIN,
            UserService.LOGIN_WITH_OTP,
            UserService.FRONT_PAGE,
        )
        for (url in urls) {
            if (contains(url)) return true
        }
        return false
    }

    private fun Request.Builder.addBearerAuthorization(): Request.Builder {
        val token = preferences.getString(SharedPrefModule.KEY_USER_TOKEN, null)
        return addHeader("token", "Bearer $token")
    }

    private fun Request.Builder.removeBearerAuthorization(): Request.Builder {
        return removeHeader("token")
    }
}