package id.io.android.olebsai.presentation.category

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.children
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseFragment
import id.io.android.olebsai.databinding.FragmentCategoryBinding
import id.io.android.olebsai.presentation.MainViewModel
import id.io.android.olebsai.presentation.home.adapter.ProductViewHolder
import id.io.android.olebsai.presentation.product.detail.ProductDetailActivity
import id.io.android.olebsai.util.viewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment :
    BaseFragment<FragmentCategoryBinding, CategoryViewModel>(R.layout.fragment_category) {
    override val binding: FragmentCategoryBinding by viewBinding(FragmentCategoryBinding::bind)
    override val vm: CategoryViewModel by viewModels()
    private val actVm: MainViewModel by activityViewModels()

    private val productListAdapter by lazy {
        ProductListVerticalAdapter(
            object : ProductViewHolder.Listener {
                override fun onProductClicked(id: String) {
                    ProductDetailActivity.start(requireContext(), id)
                }
            }
        )
    }

    companion object {
        const val CATEGORY_KEY = "category"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionView()
        setupProductList()
        observeViewModel()

        vm.getCategories()
    }

    private fun setupActionView() {
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewLifecycleOwner.lifecycleScope.launch {
                    vm.onQueryChanged(query.orEmpty())
                    productListAdapter.refresh()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        vm.onQueryChanged("")
                        productListAdapter.refresh()
                    }
                }
                return false
            }
        })

        binding.categoryChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isEmpty()) vm.onCategoryChanged(null)
            else {
                val chip = group.children.filter { (it as Chip).isChecked }.first() as Chip
                vm.onCategoryChanged(chip.tag as String)
            }
            productListAdapter.refresh()
        }
    }

    private fun setupProductList() {
        binding.rvProduct.apply {
            adapter = productListAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.products.collectLatest {
                    productListAdapter.submitData(it)
                }
            }
        }
    }

    private fun observeViewModel() {
        actVm.navigationBundle.observe(viewLifecycleOwner) {
            it?.getInt(CATEGORY_KEY)?.let { id ->
//                vm.onCategoryChanged(id)
            }
        }

        vm.categoriesResult.observe(
            onLoading = {},
            onSuccess = {
                with(binding.categoryChipGroup) {
                    it?.forEach { category ->
                        addView(Chip(context).apply {
                            val drawable = ChipDrawable.createFromAttributes(
                                context, null, 0,
                                com.google.android.material.R.style.Widget_MaterialComponents_Chip_Choice
                            )
                            setChipDrawable(drawable)
                            text = category.namaKategori
                            tag = category.kategoriId
                        })
                    }
                }
            },
            onError = {}
        )
    }
}