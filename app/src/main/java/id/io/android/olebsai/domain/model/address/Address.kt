package id.io.android.olebsai.domain.model.address

import android.os.Parcelable
import id.io.android.olebsai.data.model.entity.address.AddressEntity
import id.io.android.olebsai.domain.model.user.ApiAddress
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
    val province: ApiAddress,
    val district: ApiAddress,
    val subDistrict: ApiAddress,
    val postalCode: String,
) : Parcelable {

    fun toEntity() = AddressEntity(
        id = id,
        label = label,
        name = name,
        phone = phone,
        address = address,
        note = note,
        isDefault = isDefault,
        provinceId = province.id,
        provinceName = province.name,
        districtId = district.id,
        districtName = district.name,
        subDistrictId = subDistrict.id,
        subDistrictName = subDistrict.name,
        postalCode = postalCode,
    )
}
