package id.io.android.olebsai.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int
)