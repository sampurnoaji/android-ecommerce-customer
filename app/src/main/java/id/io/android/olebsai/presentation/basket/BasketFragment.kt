package id.io.android.olebsai.presentation.basket

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseFragment
import id.io.android.olebsai.databinding.FragmentBasketBinding
import id.io.android.olebsai.presentation.MainViewModel
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class BasketFragment :
    BaseFragment<FragmentBasketBinding, BasketViewModel>(R.layout.fragment_basket) {

    override val binding: FragmentBasketBinding by viewBinding(FragmentBasketBinding::bind)
    override val vm: BasketViewModel by viewModels()
    private val actVm: MainViewModel by activityViewModels()

    private val productBasketListAdapter by lazy { ProductBasketListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProductList()
        observeViewModel()
    }

    private fun setupProductList() {
        binding.rvProduct.apply {
            adapter = productBasketListAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    private fun observeViewModel() {
        actVm.basketProducts.observe(viewLifecycleOwner) {
            binding.groupEmpty.isVisible = it.isEmpty()
            binding.rvProduct.isVisible = it.isNotEmpty()
            productBasketListAdapter.submitList(it)
        }
    }
}