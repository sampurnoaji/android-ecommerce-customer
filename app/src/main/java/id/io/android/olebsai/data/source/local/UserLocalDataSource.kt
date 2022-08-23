package id.io.android.olebsai.data.source.local

import android.content.SharedPreferences
import id.io.android.olebsai.data.model.entity.UserEntity
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val userDao: UserDao,
    private val pref: SharedPreferences,
    private val prefEditor: SharedPreferences.Editor
) {
    suspend fun getUser(): UserEntity? = userDao.getUser()
}