package id.io.android.olebsai.presentation.order.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.io.android.olebsai.databinding.ItemOrderedProductBinding
import id.io.android.olebsai.domain.model.product.OrderedProduct
import id.io.android.olebsai.util.toRupiah

class OrderedProductListAdapter :
    ListAdapter<OrderedProduct, OrderedProductListAdapter.ContentViewHolder>(DIFF_UTIL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return ContentViewHolder(
            ItemOrderedProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ContentViewHolder(private val binding: ItemOrderedProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrderedProduct) {
            with(binding) {
                tvName.text = item.namaProduk
                tvQty.text = "${item.qty} barang"
                tvPrice.text = item.hargaTotal.toRupiah()
            }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<OrderedProduct>() {
            override fun areItemsTheSame(
                oldItem: OrderedProduct,
                newItem: OrderedProduct
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: OrderedProduct,
                newItem: OrderedProduct
            ): Boolean {
                return oldItem.pesananId == newItem.pesananId
            }
        }
    }
}
