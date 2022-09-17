package id.io.android.olebsai.domain.repository

interface UserRepository {
    fun isLoggedIn(): Boolean
    fun setLoggedIn(isLoggedIn: Boolean)
}