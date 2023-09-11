package id.io.android.olebsai.presentation.basket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseFragment
import id.io.android.olebsai.databinding.DialogUpdateNoteBinding
import id.io.android.olebsai.databinding.FragmentBasketBinding
import id.io.android.olebsai.domain.model.basket.BasketItem
import id.io.android.olebsai.presentation.order.checkout.OrderCheckoutActivity
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.toRupiah
import id.io.android.olebsai.util.ui.Dialog
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class BasketFragment :
    BaseFragment<FragmentBasketBinding, BasketViewModel>(R.layout.fragment_basket) {

    override val binding: FragmentBasketBinding by viewBinding(FragmentBasketBinding::bind)
    override val vm: BasketViewModel by viewModels()

    private val productBasketListAdapter by lazy { ProductBasketListAdapter(itemListener) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resume()
        setupView()
        setupProductList()
        observeViewModel()
    }

    fun resume() {
        vm.getBasket()
    }

    private fun setupView() {
        with(binding.toolbar) {
            imgBack.isGone = true
            tvTitle.text = getString(R.string.basket)
        }

        binding.btnBuy.setOnClickListener {
            vm.selectedProducts.value?.let { selectedProduct ->
                OrderCheckoutActivity.start(
                    requireContext(),
                    selectedProduct.map { it.second } as ArrayList<BasketItem>)
            }
        }
    }

    private fun setupProductList() {
        binding.rvProduct.apply {
            adapter = productBasketListAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun observeViewModel() {
        vm.basketItems.observe(viewLifecycleOwner) {
            if (it is LoadState.Success) {
                vm.setBasketProducts(it.data)
            }
        }

        vm.items.observe(viewLifecycleOwner) {
            binding.groupEmpty.isVisible = it.isEmpty()
            binding.rvProduct.isVisible = it.isNotEmpty()
            productBasketListAdapter.submitList(it)
        }

        vm.selectedProducts.observe(viewLifecycleOwner) { products ->
            binding.sectionButton.isVisible = products.isNotEmpty()
            binding.tvTotal.text = products.sumOf {
                it.second.qtyPembelian * (if (it.second.product.isHargaPromo) it.second.product.hargaPromo else it.second.product.hargaNormal)
            }.toRupiah()
        }

        vm.updateQtyResult.observe(
            onSuccess = {
                vm.getBasket()
            },
            onError = {
                showErrorDialog(it?.message.orEmpty())
            }
        )

        vm.updateNoteResult.observe(
            onSuccess = {
                vm.getBasket()
            },
            onError = {
                showErrorDialog(it?.message.orEmpty())
            }
        )

        vm.removeProductResult.observe(
            onSuccess = {
                vm.getBasket()
            },
            onError = {
                showErrorDialog(it?.message.orEmpty())
            }
        )
    }

    private val itemListener = object : ProductBasketListAdapter.Listener {
        override fun onCheckItem(productId: String) {
            vm.selectProduct(productId)
        }

        override fun onDeleteItem(productId: String) {
            Dialog(
                context = requireContext(),
                message = getString(R.string.basket_delete_confirmation),
                positiveButtonText = getString(R.string.basket_delete),
                positiveAction = { vm.removeProduct(productId) },
                negativeButtonText = getString(R.string.cancel),
            ).show()
        }

        override fun onMinQty(basketId: String, qty: Int) {
            vm.updateQty(basketId, qty)
        }

        override fun onAddQty(basketId: String, qty: Int) {
            vm.updateQty(basketId, qty)
        }

        override fun onNoteClicked(basketId: String, currentNote: String) {
            showUpdateNoteDialog(basketId, currentNote)
        }
    }

    private fun showUpdateNoteDialog(basketId: String, currentNote: String) {
        val binding = DialogUpdateNoteBinding.inflate(LayoutInflater.from(context))
        binding.etNote.setText(currentNote)
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .create()
        dialog.show()

        binding.btnUpdate.setOnClickListener {
            val note = binding.etNote.text?.trim()
            if (!note.isNullOrBlank()) vm.updateNote(basketId, note.toString())
            dialog.dismiss()
        }
    }
}