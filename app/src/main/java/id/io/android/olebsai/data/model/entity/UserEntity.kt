package id.io.android.olebsai.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.io.android.olebsai.domain.model.User

@Entity
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int
) {
    fun toDomain(): User = User(id = id)
}