package id.petersam.android.starter.data.source.local

import id.petersam.android.starter.data.model.entity.UserEntity
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(private val userDao: UserDao) {
    suspend fun getUser(): UserEntity? = userDao.getUser()
}