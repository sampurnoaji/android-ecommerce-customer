package id.io.android.olebsai.presentation.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import id.io.android.olebsai.databinding.ItemProductBinding
import id.io.android.olebsai.domain.model.product.WProduct
import id.io.android.olebsai.presentation.home.adapter.ProductViewHolder

class ProductListVerticalAdapter(private val listener: ProductViewHolder.Listener)
    : PagingDataAdapter<WProduct, ProductViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        if (product != null) holder.bind(product, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WProduct>() {
            override fun areItemsTheSame(oldItem: WProduct, newItem: WProduct): Boolean =
                oldItem.hashCode() == newItem.hashCode()

            override fun areContentsTheSame(oldItem: WProduct, newItem: WProduct): Boolean =
                oldItem == newItem
        }
    }
}