package id.io.android.olebsai.data.source.local.user

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import id.io.android.olebsai.di.SharedPrefModule
import id.io.android.olebsai.domain.model.user.User
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val pref: SharedPreferences,
    private val prefEditor: SharedPreferences.Editor
) {
    companion object {
        private const val PREF_KEY_LOGGED_IN = "loggedIn"
    }

    fun isLoggedIn(): Boolean = pref.getBoolean(PREF_KEY_LOGGED_IN, false)
    fun setLoggedIn(isLoggedIn: Boolean) = prefEditor.apply {
        putBoolean(PREF_KEY_LOGGED_IN, isLoggedIn)
        apply()
    }

    fun setToken(token: String) {
        prefEditor.putString(SharedPrefModule.KEY_USER_TOKEN, token)
            .apply()
    }

    fun getToken(): String = pref.getString(SharedPrefModule.KEY_USER_TOKEN, "").orEmpty()

    fun saveUser(user: User) {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(User::class.java)
        prefEditor.putString(SharedPrefModule.KEY_USER, adapter.toJson(user))
            .apply()
    }

    fun getUser(): User? {
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(User::class.java)
        val json = pref.getString(SharedPrefModule.KEY_USER, null)
        if (json != null) return adapter.fromJson(json)
        return null
    }
}