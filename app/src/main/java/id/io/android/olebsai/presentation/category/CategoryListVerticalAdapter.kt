package id.io.android.olebsai.presentation.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.io.android.olebsai.databinding.ItemCategoryVerticalBinding
import id.io.android.olebsai.domain.model.product.Category
import id.io.android.olebsai.util.dpToPx
import id.io.android.olebsai.util.ui.Selection

class CategoryListVerticalAdapter(
    private val listener: Listener
) : ListAdapter<Selection<Category>, CategoryListVerticalAdapter.ContentViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryVerticalBinding.inflate(inflater, parent, false)
        return ContentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class ContentViewHolder(private val binding: ItemCategoryVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Selection<Category>, listener: Listener) {
            val category = item.data
            val context = binding.root.context
            with(binding) {
                imgCategory.load(category.gambarIcon)
                tvName.text = category.namaKategori

                root.apply {
                    strokeWidth =
                        if (item.isSelected) 2.dpToPx(context.resources.displayMetrics)
                        else 0
                    setOnClickListener {
                        listener.onCategoryClicked(category)
                    }
                }
            }
        }
    }

    interface Listener {
        fun onCategoryClicked(category: Category)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Selection<Category>>() {
            override fun areItemsTheSame(
                oldItem: Selection<Category>,
                newItem: Selection<Category>
            ): Boolean = oldItem.data == newItem.data

            override fun areContentsTheSame(
                oldItem: Selection<Category>,
                newItem: Selection<Category>
            ): Boolean = oldItem.data.kategoriId == newItem.data.kategoriId
        }
    }
}