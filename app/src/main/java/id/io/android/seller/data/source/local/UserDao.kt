package id.io.android.seller.data.source.local

import androidx.room.Dao
import androidx.room.Query
import id.io.android.seller.data.model.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity")
    suspend fun getUser(): UserEntity?
}