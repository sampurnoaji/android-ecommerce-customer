package id.io.android.olebsai.data.model.request.address

import com.squareup.moshi.Json

data class AddAddressRequest(
    @field:Json(name = "address")
    val address: String,
    @field:Json(name = "label")
    val label: String,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "note")
    val note: String,
    @field:Json(name = "phone")
    val phone: String
)