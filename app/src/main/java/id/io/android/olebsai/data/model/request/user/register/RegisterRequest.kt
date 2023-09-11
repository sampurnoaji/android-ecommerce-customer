package id.io.android.olebsai.data.model.request.user.register


import com.squareup.moshi.Json

data class RegisterRequest(
    @field:Json(name = "email")
    val email: String,
    @field:Json(name = "namaUser")
    val namaUser: String,
    @field:Json(name = "nomorHp")
    val nomorHp: String,
    @field:Json(name = "password")
    val password: String,
    @field:Json(name = "alamat")
    val alamat: Alamat
) {
    data class Alamat(
        @field:Json(name = "alamat")
        val alamat: String,
        @field:Json(name = "kecamatan")
        val kecamatan: String,
        @field:Json(name = "kecamatanId")
        val kecamatanId: String,
        @field:Json(name = "kodePos")
        val kodePos: String,
        @field:Json(name = "kota")
        val kota: String,
        @field:Json(name = "kotaId")
        val kotaId: String,
        @field:Json(name = "provinsi")
        val provinsi: String,
        @field:Json(name = "provinsiId")
        val provinsiId: String,
    )
}
