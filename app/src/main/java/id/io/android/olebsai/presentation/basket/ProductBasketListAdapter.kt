package id.io.android.olebsai.presentation.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.io.android.olebsai.R
import id.io.android.olebsai.databinding.ItemProductBasketBinding
import id.io.android.olebsai.databinding.ItemProductBasketHeaderBinding
import id.io.android.olebsai.domain.model.product.Product
import id.io.android.olebsai.util.toRupiah
import id.io.android.olebsai.util.ui.Selection

class ProductBasketListAdapter(private val listener: Listener) :
    ListAdapter<ProductBasketListAdapter.Item, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == HEADER_TYPE)
            (holder as HeaderViewHolder).bind(getItem(position).header)
        else
            (holder as ContentViewHolder).bind(getItem(position).content, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HEADER_TYPE) HeaderViewHolder(
            ItemProductBasketHeaderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) else ContentViewHolder(
            ItemProductBasketBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class HeaderViewHolder(private val binding: ItemProductBasketHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(shopName: String?) {
            binding.tvShopName.text = shopName
        }
    }

    class ContentViewHolder(private val binding: ItemProductBasketBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Pair<Int, Selection<Product>>?, listener: Listener) {
            item?.let {
                with(it.second.data) {
                    binding.imgProduct.load(imageUrl) {
                        placeholder(R.color.background)
                    }
                    binding.tvQty.text =
                        binding.root.context.getString(R.string.product_count_qty, it.first)
                    binding.tvName.text = name
                    binding.tvPrice.text = price.toRupiah()
                    binding.tvOriginalPrice.text = originalPrice.toRupiah()
                    binding.tvPercentDiscount.text = "${percentDiscount}%"

                    binding.imgCheck.apply {
                        setImageResource(
                            if (item.second.isSelected) R.drawable.ic_baseline_check_box_24
                            else R.drawable.ic_baseline_check_box_outline_blank_24
                        )
                        setOnClickListener {
                            listener.onCheckItem(this@with.id)
                        }
                    }

                    binding.imgDelete.setOnClickListener {
                        listener.onDeleteItem(this@with.id)
                    }
                }
            }
        }
    }

    data class Item(
        val viewType: Int,
        val header: String? = null,
        val content: Pair<Int, Selection<Product>>? = null
    )

    companion object {
        const val HEADER_TYPE = 0
        const val CONTENT_TYPE = 1

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(
                oldItem: Item,
                newItem: Item
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: Item,
                newItem: Item
            ): Boolean = oldItem.content?.second?.data?.id == newItem.content?.second?.data?.id
        }
    }

    interface Listener {
        fun onCheckItem(productId: Int)
        fun onDeleteItem(productId: Int)
    }
}