package id.io.android.olebsai.presentation.account.address.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.io.android.olebsai.R
import id.io.android.olebsai.databinding.ItemAddressBinding
import id.io.android.olebsai.domain.model.address.Address

class AddressListAdapter(private val listener: Listener) :
    ListAdapter<Address, AddressListAdapter.ContentViewHolder>(DIFF_UTIL) {

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
        fun bind(item: Address, listener: Listener) {
            with(binding) {
                root.setOnClickListener {
                    listener.onSelectAddress(item)
                }

                imgCheck.apply {
                    setImageResource(
                        if (item.isDefault) R.drawable.ic_baseline_check_box_24
                        else R.drawable.ic_baseline_check_box_outline_blank_24
                    )
                    setOnClickListener {
                        if (!item.isDefault) listener.onSetAddressDefault(item)
                    }
                }

                tvLabelName.text = "${item.label} - ${item.name}"
                tvPhone.text = item.phone
                tvAddress.text = item.address
                tvAddressNote.text = item.note
            }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Address>() {
            override fun areItemsTheSame(
                oldItem: Address,
                newItem: Address
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: Address,
                newItem: Address
            ): Boolean = oldItem.id == newItem.id
        }
    }

    interface Listener {
        fun onSelectAddress(address: Address)
        fun onSetAddressDefault(address: Address)
    }
}