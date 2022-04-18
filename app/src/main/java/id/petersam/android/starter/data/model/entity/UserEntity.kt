package id.petersam.android.starter.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.petersam.android.starter.domain.model.User

@Entity
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int
) {
    fun toDomain(): User = User(id = id)
}