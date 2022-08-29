package id.io.android.olebsai.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.io.android.olebsai.databinding.ItemVoucherBinding
import id.io.android.olebsai.domain.model.voucher.Voucher

class VoucherListAdapter(private val vouchers: List<Voucher>) :
    RecyclerView.Adapter<VoucherListAdapter.ContentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemVoucherBinding.inflate(inflater, parent, false)
        return ContentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(vouchers[position])
    }

    override fun getItemCount(): Int {
        return vouchers.size
    }

    class ContentViewHolder(private val binding: ItemVoucherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(voucher: Voucher) {
            binding.tvName.text = voucher.name
            binding.tvDesc.text = voucher.desc
        }
    }
}