package id.io.android.olebsai.domain.model.address

import android.os.Parcelable
import id.io.android.olebsai.data.model.entity.address.AddressEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val id: Int,
    val label: String,
    val name: String,
    val phone: String,
    val address: String,
    val note: String,
    val isDefault: Boolean,
) : Parcelable {

    fun toEntity() = AddressEntity(
        id = id,
        label = label,
        name = name,
        phone = phone,
        address = address,
        note = note,
        isDefault = isDefault,
    )
}
