package id.io.android.olebsai.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.io.android.olebsai.databinding.ItemCategoryBinding
import id.io.android.olebsai.domain.model.category.Category

class CategoryListAdapter(private val categories: List<Category>) :
    RecyclerView.Adapter<CategoryListAdapter.ContentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater, parent, false)
        return ContentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    class ContentViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.imgCategory.setImageResource(category.type.imageRes)
            binding.tvName.text = binding.root.context.getString(category.type.stringRes)
        }
    }
}