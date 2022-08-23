package id.io.android.olebsai.data.source.local

import androidx.room.Dao
import androidx.room.Query
import id.io.android.olebsai.data.model.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity")
    suspend fun getUser(): UserEntity?
}