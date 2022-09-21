package id.io.android.olebsai.presentation.order.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.io.android.olebsai.databinding.ItemTrxBinding
import id.io.android.olebsai.domain.model.order.Trx
import id.io.android.olebsai.util.toRupiah

class TrxListAdapter : ListAdapter<Trx, TrxListAdapter.ContentViewHolder>(DIFF_UTIL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return ContentViewHolder(
            ItemTrxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ContentViewHolder(private val binding: ItemTrxBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Trx) {
            with(binding) {
                tvOrderDate.text = item.date
                tvStatus.text = item.status
                imgProduct.load(item.products[0].imageUrl)
                tvProductName.text = item.products[0].name
                tvProductCount.text = "${item.products.size} Barang"
                tvTotal.text = item.products.sumOf { it.price }.toRupiah()
            }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Trx>() {
            override fun areItemsTheSame(oldItem: Trx, newItem: Trx): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Trx, newItem: Trx): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}