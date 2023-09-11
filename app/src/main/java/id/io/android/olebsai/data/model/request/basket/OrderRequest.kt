package id.io.android.olebsai.data.model.request.basket

import com.squareup.moshi.Json

data class OrderRequest(
    @field:Json(name = "pesananHeaderId")
    val pesananHeaderId: String
)
