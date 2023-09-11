package id.io.android.olebsai.data.model.entity.address

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.io.android.olebsai.domain.model.address.Address
import id.io.android.olebsai.domain.model.user.ApiAddress

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
    val phone: String,
    @ColumnInfo(name = "provinceId")
    val provinceId: String,
    @ColumnInfo(name = "provinceName")
    val provinceName: String,
    @ColumnInfo(name = "districtId")
    val districtId: String,
    @ColumnInfo(name = "districtName")
    val districtName: String,
    @ColumnInfo(name = "subDistrictId")
    val subDistrictId: String,
    @ColumnInfo(name = "subDistrictName")
    val subDistrictName: String,
    @ColumnInfo(name = "postalCode")
    val postalCode: String,
) {
    fun toDomain() = Address(
        id = id,
        label = label,
        name = name,
        phone = phone,
        address = address,
        note = note,
        isDefault = isDefault,
        province = ApiAddress(id = provinceId, name = provinceName),
        district = ApiAddress(id = districtId, name = districtName),
        subDistrict = ApiAddress(id = subDistrictId, name = subDistrictName),
        postalCode = postalCode
    )
}