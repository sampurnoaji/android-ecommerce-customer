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
import id.io.android.olebsai.presentation.MainActivity
import id.io.android.olebsai.presentation.MainViewModel
import id.io.android.olebsai.util.toRupiah
import id.io.android.olebsai.util.ui.Dialog
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class BasketFragment :
    BaseFragment<FragmentBasketBinding, BasketViewModel>(R.layout.fragment_basket) {

    override val binding: FragmentBasketBinding by viewBinding(FragmentBasketBinding::bind)
    override val vm: BasketViewModel by viewModels()
    private val actVm: MainViewModel by activityViewModels()

    private val productBasketListAdapter by lazy {
        ProductBasketListAdapter(
            object : ProductBasketListAdapter.Listener {
                override fun onCheckItem(productId: Int) {
                    vm.selectProduct(productId)
                }

                override fun onDeleteItem(productId: Int) {
                    Dialog(
                        context = requireContext(),
                        message = getString(R.string.basket_delete_confirmation),
                        positiveButtonText = getString(R.string.basket_delete),
                        positiveAction = { vm.deleteProduct(productId) },
                        negativeButtonText = getString(R.string.cancel),
                    ).show()
                }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resume()
        setupView()
        setupProductList()
        observeViewModel()
    }

    fun resume() {
        if (actVm.isLoggedIn.value == false) {
            (requireActivity() as MainActivity).navigateToLogin()
        }
    }

    private fun setupView() {
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun setupProductList() {
        binding.rvProduct.apply {
            adapter = productBasketListAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
    }

    private fun observeViewModel() {
        actVm.basketProducts.observe(viewLifecycleOwner) { products ->
            vm.setBasketProducts(products)
        }

        vm.items.observe(viewLifecycleOwner) {
            binding.groupEmpty.isVisible = it.isEmpty()
            binding.rvProduct.isVisible = it.isNotEmpty()
            productBasketListAdapter.submitList(it)
        }

        vm.selectedProducts.observe(viewLifecycleOwner) { products ->
            binding.sectionButton.isVisible = products.isNotEmpty()
            binding.tvTotal.text = products.sumOf { it.price }.toRupiah()
        }
    }
}