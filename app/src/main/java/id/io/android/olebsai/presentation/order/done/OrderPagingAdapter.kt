package id.io.android.olebsai.presentation.order.done

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import id.io.android.olebsai.databinding.ItemOrderBinding
import id.io.android.olebsai.domain.model.order.Order
import id.io.android.olebsai.presentation.order.done.OrderPagingAdapter.ContentViewHolder
import id.io.android.olebsai.presentation.order.history.OrderListAdapter
import id.io.android.olebsai.presentation.order.history.setValue

class OrderPagingAdapter(private val listener: Listener)
    : PagingDataAdapter<Order, ContentViewHolder>(OrderListAdapter.DIFF_UTIL) {

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val order = getItem(position)
        if (order != null) holder.bind(order)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return ContentViewHolder(
            ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
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

    interface Listener {
        fun onItemClicked(order: Order)
    }
}