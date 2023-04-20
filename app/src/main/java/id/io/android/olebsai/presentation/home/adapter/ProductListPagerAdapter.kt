package id.io.android.olebsai.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.io.android.olebsai.databinding.ItemProductBinding
import id.io.android.olebsai.domain.model.product.Product
import id.io.android.olebsai.util.toRupiah

class ProductListPagerAdapter(private val listener: Listener)
    : PagingDataAdapter<Product, ProductListPagerAdapter.ContentViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val product = getItem(position)
        if (product != null) holder.bind(product, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return ContentViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class ContentViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, listener: Listener) {
            binding.imgProduct.load(product.imageUrl) {
                placeholder(android.R.color.darker_gray)
                error(android.R.color.darker_gray)
            }
            binding.tvName.text = product.name
            binding.tvPrice.text = product.price.toRupiah()
            binding.tvOriginalPrice.text = product.originalPrice.toRupiah()
            binding.tvPercentDiscount.text = "${product.percentDiscount}%"
            binding.tvRating.text = product.rating.toString()
            binding.tvSoldCount.text = "Terjual ${product.soldCount}"

            binding.root.setOnClickListener {
                listener.onProductClicked(product.id)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.hashCode() == newItem.hashCode()

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }

    interface Listener {
        fun onProductClicked(id: Int)
    }
}