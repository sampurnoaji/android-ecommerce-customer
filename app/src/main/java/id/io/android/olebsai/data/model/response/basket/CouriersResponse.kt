package id.io.android.olebsai.data.model.response.basket


import com.squareup.moshi.Json
import id.io.android.olebsai.domain.model.basket.Courier
import id.io.android.olebsai.domain.model.basket.Courier.Service

data class CouriersResponse(
    @field:Json(name = "data")
    val data: List<CourierResponse>
) {
    data class CourierResponse(
        @field:Json(name = "gambarLogo")
        val gambarLogo: Any? = null,
        @field:Json(name = "kodeKurir")
        val kodeKurir: String? = null,
        @field:Json(name = "kurirId")
        val kurirId: String? = null,
        @field:Json(name = "namaKurir")
        val namaKurir: String? = null,
        @field:Json(name = "servis")
        val services: List<ServiceResponse>? = null,
    ) {
        data class ServiceResponse(
            @field:Json(name = "deskripsi")
            val deskripsi: String? = null,
            @field:Json(name = "estimasiSampai")
            val estimasiSampai: String? = null,
            @field:Json(name = "namaServis")
            val namaServis: String? = null,
            @field:Json(name = "ongkir")
            val ongkir: Long? = null,
        ) {
            fun toDomain() = Service(
                deskripsi = deskripsi.orEmpty(),
                estimasiSampai = estimasiSampai.orEmpty(),
                namaServis = namaServis.orEmpty(),
                ongkir = ongkir ?: 0
            )
        }

        fun toDomain() = Courier(
            gambarLogo = gambarLogo.toString(),
            kodeKurir = kodeKurir.orEmpty(),
            kurirId = kurirId.orEmpty(),
            namaKurir = namaKurir.orEmpty(),
            services = services?.map { it.toDomain() }.orEmpty()
        )
    }
}