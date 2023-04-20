package id.io.android.olebsai.presentation.home.adapter

import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.io.android.olebsai.R
import id.io.android.olebsai.databinding.ItemProductBinding
import id.io.android.olebsai.domain.model.product.WProduct
import id.io.android.olebsai.util.toRupiah

class ProductViewHolder(private val binding: ItemProductBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(product: WProduct, listener: Listener) {
        with(binding) {
            imgProduct.load(product.listPicture.firstOrNull()?.url) {
                placeholder(R.color.background)
                error(android.R.color.darker_gray)
            }
            tvName.text = product.namaProduk
            if (product.isHargaPromo) {
                tvPrice.text = product.hargaPromo.toRupiah()
                tvOriginalPrice.text = product.hargaNormal.toRupiah()
                tvPercentDiscount.text = "${product.discount()}%"
            } else {
                tvPrice.text = product.hargaNormal.toRupiah()
                tvOriginalPrice.isGone = true
                tvPercentDiscount.isGone = true
            }
//                tvRating.text = product.rating.toString()
            tvSoldCount.text = "Terjual ${product.qtyTerjual}"

            binding.root.setOnClickListener {
                listener.onProductClicked(product.produkId)
            }
        }
    }

    interface Listener {
        fun onProductClicked(id: String)
    }
}