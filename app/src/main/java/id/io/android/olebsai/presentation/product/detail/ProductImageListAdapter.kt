package id.io.android.olebsai.presentation.product.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.io.android.olebsai.R
import id.io.android.olebsai.databinding.ItemProductImageBinding

class ProductImageListAdapter :
    ListAdapter<String, ProductImageListAdapter.ViewPagerViewHolder>(DIFF_UTIL) {

    inner class ViewPagerViewHolder(val binding: ItemProductImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(imageUrl: String) {
            binding.image.load(imageUrl) {
                placeholder(R.color.background)
                error(android.R.color.darker_gray)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding = ItemProductImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.setData(getItem(position))
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }
}