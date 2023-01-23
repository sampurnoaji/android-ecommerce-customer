package id.io.android.olebsai.data.model.response.address


import com.squareup.moshi.Json
import id.io.android.olebsai.domain.model.address.Address

data class AddressListResponse(
    @field:Json(name = "addresses")
    val addresses: List<AddressResponse?>?
) {
    data class AddressResponse(
        @field:Json(name = "address")
        val address: String?,
        @field:Json(name = "id")
        val id: Int?,
        @field:Json(name = "isDefault")
        val isDefault: Boolean?,
        @field:Json(name = "label")
        val label: String?,
        @field:Json(name = "name")
        val name: String?,
        @field:Json(name = "note")
        val note: String?,
        @field:Json(name = "phone")
        val phone: String?
    )

    fun toAddressList(): List<Address> {
        return addresses?.map {
            Address(
                id = it?.id ?: 0,
                address = it?.address.orEmpty(),
                isDefault = it?.isDefault ?: false,
                label = it?.label.orEmpty(),
                name = it?.name.orEmpty(),
                note = it?.note.orEmpty(),
                phone = it?.phone.orEmpty(),
            )
        } ?: emptyList()
    }
}
