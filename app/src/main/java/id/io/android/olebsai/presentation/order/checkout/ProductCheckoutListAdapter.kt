package id.io.android.olebsai.presentation.order.checkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.io.android.olebsai.R
import id.io.android.olebsai.databinding.ItemProductCheckoutBinding
import id.io.android.olebsai.domain.model.basket.BasketItem
import id.io.android.olebsai.util.toRupiah

class ProductCheckoutListAdapter :
    ListAdapter<BasketItem, ProductCheckoutListAdapter.ContentViewHolder>(DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return ContentViewHolder(
            ItemProductCheckoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class ContentViewHolder(private val binding: ItemProductCheckoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BasketItem) {
            with(binding) {
                with(item.product) {
                    imgProduct.load(listPicture.getOrNull(0)?.url.orEmpty()) {
                        placeholder(R.color.background)
                        error(android.R.color.darker_gray)
                    }
                    tvName.text = namaProduk
                    if (isHargaPromo) {
                        tvPrice.text = hargaPromo.toRupiah()
                        tvOriginalPrice.text = hargaNormal.toRupiah()
                        tvPercentDiscount.text = "${discount()}%"
                    } else {
                        tvPrice.text = hargaNormal.toRupiah()
                        tvOriginalPrice.isGone = true
                        tvPercentDiscount.isGone = true
                    }
                    tvQty.text =
                        root.context.getString(R.string.product_count_qty, item.qtyPembelian)
                    tvNote.text = item.catatan
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<BasketItem>() {
            override fun areItemsTheSame(
                oldItem: BasketItem,
                newItem: BasketItem
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: BasketItem,
                newItem: BasketItem
            ): Boolean = oldItem.hashCode() == newItem.hashCode()
        }
    }
}