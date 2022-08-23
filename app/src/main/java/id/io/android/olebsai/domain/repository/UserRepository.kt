package id.io.android.olebsai.domain.repository

import id.io.android.olebsai.domain.model.User
import id.io.android.olebsai.util.LoadState

interface UserRepository {
    suspend fun getUser(): LoadState<User?>
}