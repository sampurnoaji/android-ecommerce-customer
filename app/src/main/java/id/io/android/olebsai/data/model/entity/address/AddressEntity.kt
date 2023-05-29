package id.io.android.olebsai.data.model.entity.address

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.io.android.olebsai.domain.model.address.Address

@Entity
data class AddressEntity(
    @ColumnInfo(name = "address")
    val address: String,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "isDefault")
    val isDefault: Boolean,
    @ColumnInfo(name = "label")
    val label: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "note")
    val note: String,
    @ColumnInfo(name = "phone")
    val phone: String
) {
    fun toDomain() = Address(
        id = id,
        label = label,
        name = name,
        phone = phone,
        address = address,
        note = note,
        isDefault = isDefault,
    )
}