package id.io.android.olebsai.presentation.order.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.io.android.olebsai.R
import id.io.android.olebsai.databinding.ItemOrderBinding
import id.io.android.olebsai.domain.model.order.Order
import id.io.android.olebsai.util.toRupiah

class OrderListAdapter(private val listener: Listener) :
    ListAdapter<Order, OrderListAdapter.ContentViewHolder>(DIFF_UTIL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return ContentViewHolder(
            ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ContentViewHolder(private val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Order) {
            binding.setValue(item)
            binding.root.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Order>() {
            override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
                return oldItem.headerId == newItem.headerId
            }
        }
    }

    interface Listener {
        fun onItemClicked(order: Order)
    }
}

fun ItemOrderBinding.setValue(order: Order) {
    tvShopName.text = order.namaToko
    tvOrderId.text = "#${order.nomorPesanan}"
    tvStatus.text = order.status.status

    when (order.status) {
        Order.Status.SELESAI -> {
            tvStatus.setBackgroundResource(R.drawable.text_chip_green)
            tvOrderDate.text = order.tglCheckout
        }
        Order.Status.DIKEMAS -> {
            labelOrderDate.text = root.context.getString(R.string.order_date_paid)
            tvOrderDate.text = order.tglBayar
        }
        Order.Status.BELUM_BAYAR -> {
            tvOrderDate.text = order.tglCheckout
        }
        else -> {
            tvOrderDate.text = order.tglCheckout
        }
    }

    tvCourierName.text = order.namaJasaPengiriman
    tvCourierEstimate.text = "${order.servisJasaPengiriman} ${order.estimasiSampai} hari"
    tvTotal.text = order.totalBayar.toRupiah()
}