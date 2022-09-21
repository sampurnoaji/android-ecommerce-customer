package id.io.android.olebsai.presentation.order.history

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.model.order.Trx
import id.io.android.olebsai.domain.model.product.Product
import javax.inject.Inject

@HiltViewModel
class TrxViewModel @Inject constructor(): ViewModel() {

    val trxs = listOf(
        Trx(
            id = 1,
            date = "12 Sep 2022",
            status = "Sedang Dikirim",
            products = listOf(
                Product(
                    id = 1,
                    name = "Homyped Wanita Sandal-Violeta-N45MarunMuda",
                    imageUrl = "https://s3.bukalapak.com/img/30817458592/large/data.jpeg.webp",
                    price = 100000,
                    originalPrice = 699900,
                    percentDiscount = 86,
                    rating = 4.9f,
                    soldCount = 24,
                    shopName = "Toko Maju Jaya",
                    condition = "Baru",
                    dimension = "10 gram",
                    minOrder = "1 Buah",
                    category = "Elektronik",
                    description = "Beli aneka produk di Toko Gondray store secara online sekarang. Kamu bisa beli produk dari Toko Gondray store dengan aman &amp; mudah dari Kota Bekasi. Ingin belanja lebih hemat &amp; terjangkau di Toko Gondray store?"
                )
            )
        )
    )
}