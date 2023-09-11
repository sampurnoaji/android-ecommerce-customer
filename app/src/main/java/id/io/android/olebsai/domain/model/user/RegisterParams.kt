package id.io.android.olebsai.domain.model.user

import id.io.android.olebsai.data.model.request.user.UpdateProfileRequest
import id.io.android.olebsai.data.model.request.user.register.RegisterRequest

data class RegisterParams(
    val email: String,
    val name: String,
    val phoneNumber: String,
    val password: String,
    val address: User.Address,
) {
    fun toRequest() = RegisterRequest(
        email = email,
        namaUser = name,
        password = password,
        nomorHp = phoneNumber,
        alamat = RegisterRequest.Alamat(
            alamat = address.alamat,
            kecamatan = address.kecamatan,
            kecamatanId = address.kecamatanId,
            kodePos = address.kodePos,
            kota = address.kota,
            kotaId = address.kotaId,
            provinsi = address.provinsi,
            provinsiId = address.provinsiId,
        )
    )

    fun toUpdateProfileRequest() = UpdateProfileRequest(
        email = email,
        namaUser = name,
        nomorHp = phoneNumber,
        alamat = RegisterRequest.Alamat(
            alamat = address.alamat,
            kecamatan = address.kecamatan,
            kecamatanId = address.kecamatanId,
            kodePos = address.kodePos,
            kota = address.kota,
            kotaId = address.kotaId,
            provinsi = address.provinsi,
            provinsiId = address.provinsiId,
        )
    )
}
