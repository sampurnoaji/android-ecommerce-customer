package id.io.android.seller.data.source.local

import id.io.android.seller.data.model.entity.UserEntity
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(private val userDao: UserDao) {
    suspend fun getUser(): UserEntity? = userDao.getUser()
}