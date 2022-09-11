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
        fun bind(item: Selection<Product>?, listener: Listener) {
            with(binding) {
                item?.let {
                    imgProduct.load(item.data.imageUrl) {
                        placeholder(R.color.background)
                    }
                    tvName.text = item.data.name
                    tvPrice.text = item.data.price.toRupiah()
                    tvOriginalPrice.text = item.data.originalPrice.toRupiah()
                    tvPercentDiscount.text = "${item.data.percentDiscount}%"

                    imgCheck.apply {
                        setImageResource(
                            if (item.isSelected) R.drawable.ic_baseline_check_box_24
                            else R.drawable.ic_baseline_check_box_outline_blank_24
                        )
                        setOnClickListener {
                            listener.onCheckItem(item.data.id)
                        }
                    }

                    imgDelete.setOnClickListener {
                        listener.onDeleteItem(item.data.id)
                    }
                }
            }
        }
    }

    data class Item(
        val viewType: Int,
        val header: String? = null,
        val content: Selection<Product>? = null
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
            ): Boolean = oldItem.content?.data?.id == newItem.content?.data?.id
        }
    }

    interface Listener {
        fun onCheckItem(productId: Int)
        fun onDeleteItem(productId: Int)
    }
}