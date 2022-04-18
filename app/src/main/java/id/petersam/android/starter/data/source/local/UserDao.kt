package id.petersam.android.starter.data.source.local

import androidx.room.Dao
import androidx.room.Query
import id.petersam.android.starter.data.model.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity")
    suspend fun getUser(): UserEntity?
}