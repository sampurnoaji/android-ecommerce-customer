package id.io.android.olebsai.data.source.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import id.io.android.olebsai.data.model.entity.UserEntity
import id.io.android.olebsai.data.model.entity.address.AddressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity")
    suspend fun getUser(): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAddress(addressEntity: AddressEntity)

    @Update
    suspend fun updateAddress(addressEntity: AddressEntity)

    @Query("SELECT * FROM AddressEntity")
    fun getAddressList(): Flow<List<AddressEntity>>

    @Update
    suspend fun setDefaultAddress(addressEntity: AddressEntity)

    @Query("SELECT * FROM AddressEntity WHERE isDefault is 1 LIMIT 1")
    suspend fun getDefaultAddress(): AddressEntity?

    @Query("DELETE FROM AddressEntity WHERE id = :id")
    suspend fun deleteAddress(id: Int)
}