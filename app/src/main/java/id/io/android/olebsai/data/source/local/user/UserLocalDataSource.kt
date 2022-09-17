package id.io.android.olebsai.data.source.local.user

import android.content.SharedPreferences
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
}