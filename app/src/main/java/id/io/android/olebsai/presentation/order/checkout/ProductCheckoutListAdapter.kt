package id.io.android.olebsai.presentation.order.checkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.io.android.olebsai.R
import id.io.android.olebsai.databinding.ItemProductCheckoutBinding
import id.io.android.olebsai.util.toRupiah

class ProductCheckoutListAdapter :
    ListAdapter<ProductCheckout, ProductCheckoutListAdapter.ContentViewHolder>(DIFF_CALLBACK) {
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
        fun bind(item: ProductCheckout) {
            with(binding) {
                imgProduct.load(item.imageUrl) {
                    placeholder(R.color.background)
                }
                tvQty.text = root.context.getString(R.string.product_count_qty, item.qty)
                tvName.text = item.name
                tvPrice.text = item.total.toRupiah()
                tvOriginalPrice.text = item.originalPrice.toRupiah()
                tvPercentDiscount.text = "${item.discount}%"
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ProductCheckout>() {
            override fun areItemsTheSame(
                oldItem: ProductCheckout,
                newItem: ProductCheckout
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: ProductCheckout,
                newItem: ProductCheckout
            ): Boolean = oldItem.hashCode() == newItem.hashCode()
        }
    }
}