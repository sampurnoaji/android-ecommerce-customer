package id.io.android.olebsai.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.io.android.olebsai.databinding.ItemCategoryBinding
import id.io.android.olebsai.domain.model.product.Category
import id.io.android.olebsai.presentation.home.adapter.CategoryListAdapter.ContentViewHolder

class CategoryListAdapter(private val listener: Listener) :
    ListAdapter<Category, ContentViewHolder>(DIFF_UTIL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return ContentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class ContentViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category, listener: Listener) {
            with(binding) {
                imgCategory.load(category.gambarIcon)
                tvName.text = category.namaKategori

                root.setOnClickListener {
                    listener.onCategoryClicked(category)
                }
            }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
                return oldItem.kategoriId == newItem.kategoriId
            }
        }
    }

    interface Listener {
        fun onCategoryClicked(category: Category)
    }
}