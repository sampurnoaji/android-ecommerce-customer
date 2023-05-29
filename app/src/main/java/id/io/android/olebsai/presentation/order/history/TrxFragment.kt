package id.io.android.olebsai.presentation.order.history

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseFragment
import id.io.android.olebsai.databinding.FragmentTrxBinding
import id.io.android.olebsai.util.viewBinding

class TrxFragment : BaseFragment<FragmentTrxBinding, TrxViewModel>(R.layout.fragment_trx) {

    override val binding by viewBinding(FragmentTrxBinding::bind)
    override val vm: TrxViewModel by viewModels()

    private val trxListAdapter by lazy { TrxListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        with(binding.toolbar) {
            imgBack.isGone = true
            tvTitle.text = getString(R.string.trx)
        }

        with(binding.rvTrx) {
            adapter = trxListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        trxListAdapter.submitList(vm.trxs)
    }
}