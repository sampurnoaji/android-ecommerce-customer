package id.petersam.android.starter.domain.repository

import id.petersam.android.starter.domain.model.User

interface UserRepository {
    suspend fun getUser(id: Int): User
}