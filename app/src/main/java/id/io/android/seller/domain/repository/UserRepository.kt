package id.io.android.seller.domain.repository

import id.io.android.seller.domain.model.User

interface UserRepository {
    suspend fun getUser(): User?
}