package id.io.android.olebsai.presentation.account.address.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.io.android.olebsai.R
import id.io.android.olebsai.databinding.ItemAddressBinding
import id.io.android.olebsai.domain.model.address.Address
import id.io.android.olebsai.util.ui.Selection

class AddressListAdapter(private val listener: Listener) :
    ListAdapter<Selection<Address>, AddressListAdapter.ContentViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return ContentViewHolder(
            ItemAddressBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class ContentViewHolder(private val binding: ItemAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Selection<Address>, listener: Listener) {
            with(binding) {
                imgCheck.apply {
                    setImageResource(
                        if (item.isSelected) R.drawable.ic_baseline_check_box_24
                        else R.drawable.ic_baseline_check_box_outline_blank_24
                    )
                    setOnClickListener {
                        listener.onSelectAddress(item.data.id)
                    }
                }

                tvLabelName.text = "${item.data.label} - ${item.data.name}"
                tvPhone.text = item.data.phone
                tvAddress.text = item.data.address
                tvAddressNote.text = item.data.note
            }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Selection<Address>>() {
            override fun areItemsTheSame(
                oldItem: Selection<Address>,
                newItem: Selection<Address>
            ): Boolean = oldItem.data == newItem.data

            override fun areContentsTheSame(
                oldItem: Selection<Address>,
                newItem: Selection<Address>
            ): Boolean = oldItem.isSelected == newItem.isSelected
        }
    }

    interface Listener {
        fun onSelectAddress(id: Int)
    }
}