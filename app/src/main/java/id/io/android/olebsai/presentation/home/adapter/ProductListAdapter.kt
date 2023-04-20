package id.io.android.olebsai.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import id.io.android.olebsai.databinding.ItemProductBinding
import id.io.android.olebsai.domain.model.product.WProduct

class ProductListAdapter(private val listener: ProductViewHolder.Listener) :
    ListAdapter<WProduct, ProductViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(currentList[position], listener)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<WProduct>() {
            override fun areItemsTheSame(oldItem: WProduct, newItem: WProduct): Boolean =
                oldItem.produkId == newItem.produkId

            override fun areContentsTheSame(oldItem: WProduct, newItem: WProduct): Boolean =
                oldItem == newItem
        }
    }
}