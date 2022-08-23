package id.petersam.android.starter.domain.repository

import id.petersam.android.starter.domain.model.User
import id.petersam.android.starter.util.LoadState

interface UserRepository {
    suspend fun getUser(): LoadState<User?>
}