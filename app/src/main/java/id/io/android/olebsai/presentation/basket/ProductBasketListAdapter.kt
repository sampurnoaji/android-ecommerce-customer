package id.io.android.olebsai.presentation.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import id.io.android.olebsai.R
import id.io.android.olebsai.databinding.ItemProductBasketBinding
import id.io.android.olebsai.databinding.ItemProductBasketHeaderBinding
import id.io.android.olebsai.domain.model.basket.BasketItem
import id.io.android.olebsai.util.toRupiah
import id.io.android.olebsai.util.ui.Selection

class ProductBasketListAdapter(private val listener: Listener) :
    ListAdapter<ProductBasketListAdapter.Item, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == HEADER_TYPE)
            (holder as HeaderViewHolder).bind(getItem(position).header.orEmpty())
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
        fun bind(shopName: String) {
            binding.tvShopName.text = shopName
        }
    }

    class ContentViewHolder(private val binding: ItemProductBasketBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Pair<Int, Selection<BasketItem>>?, listener: Listener) {
            item?.let {
                it.second.data.run {
                    with(binding) {
                        imgProduct.load(product.listPicture.getOrNull(0)?.url.orEmpty()) {
                            placeholder(R.color.background)
                            error(android.R.color.darker_gray)
                        }
                        tvQty.text = it.second.data.qtyPembelian.toString()
                        tvName.text = product.namaProduk
                        if (product.isHargaPromo) {
                            tvPrice.text = product.hargaPromo.toRupiah()
                            tvOriginalPrice.text = product.hargaNormal.toRupiah()
                            tvPercentDiscount.text = "${product.discount()}%"
                        } else {
                            tvPrice.text = product.hargaNormal.toRupiah()
                            tvOriginalPrice.isGone = true
                            tvPercentDiscount.isGone = true
                        }
                        imgCheck.apply {
                            setImageResource(
                                if (item.second.isSelected) R.drawable.ic_baseline_check_box_24
                                else R.drawable.ic_baseline_check_box_outline_blank_24
                            )
                            setOnClickListener {
                                listener.onCheckItem(product.produkId)
                            }
                        }

                        imgDelete.setOnClickListener {
                            listener.onDeleteItem(product.produkId)
                        }

                        tvNote.apply {
                            text =
                                catatan.ifEmpty { root.context.getString(R.string.basket_note_add) }
                            setOnClickListener { listener.onNoteClicked(keranjangId, catatan) }
                        }

                        btnMin.setOnClickListener {
                            val currentQty = tvQty.text.toString().toIntOrNull() ?: 0
                            if (currentQty > 1) {
                                listener.onMinQty(keranjangId, currentQty - 1)
                            } else if (currentQty > 0) {
                                listener.onDeleteItem(product.produkId)
                            }
                        }

                        btnAdd.setOnClickListener {
                            val currentQty = tvQty.text.toString().toIntOrNull() ?: 0
                            listener.onAddQty(keranjangId, currentQty + 1)
                        }
                    }
                }
            }
        }
    }

    data class Item(
        val viewType: Int,
        val header: String? = null,
        val content: Pair<Int, Selection<BasketItem>>? = null
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
            ): Boolean =
                oldItem.content?.second?.data?.keranjangId == newItem.content?.second?.data?.keranjangId
        }
    }

    interface Listener {
        fun onCheckItem(productId: String)
        fun onDeleteItem(productId: String)
        fun onMinQty(basketId: String, qty: Int)
        fun onAddQty(basketId: String, qty: Int)
        fun onNoteClicked(basketId: String, currentNote: String)
    }
}