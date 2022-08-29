package id.io.android.olebsai.presentation.category

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseFragment
import id.io.android.olebsai.databinding.FragmentCategoryBinding
import id.io.android.olebsai.presentation.MainViewModel
import id.io.android.olebsai.util.viewBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment :
    BaseFragment<FragmentCategoryBinding, CategoryViewModel>(R.layout.fragment_category) {
    override val binding: FragmentCategoryBinding by viewBinding(FragmentCategoryBinding::bind)
    override val vm: CategoryViewModel by viewModels()
    private val actVm: MainViewModel by activityViewModels()

    private val categoryListAdapter by lazy {
        CategoryListVerticalAdapter(object : CategoryListVerticalAdapter.Listener {
            override fun onCategoryClicked(id: Int) {
                vm.onCategoryChanged(id)
            }
        })
    }
    private val productListAdapter by lazy { ProductListVerticalAdapter() }

    companion object {
        const val CATEGORY_KEY = "category"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCategoryList()
        setupProductList()
        observeViewModel()
    }

    private fun setupCategoryList() {
        binding.rvCategory.apply {
            adapter = categoryListAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun setupProductList() {
        binding.rvProduct.apply {
            adapter = productListAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.products.collectLatest {
                    productListAdapter.submitData(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                productListAdapter.loadStateFlow.collect {
                    binding.appendProgress.isVisible = it.source.append is LoadState.Loading
                }
            }
        }
    }

    private fun observeViewModel() {
        actVm.navigationBundle.observe(viewLifecycleOwner) {
            it?.getInt(CATEGORY_KEY)?.let { id ->
                vm.onCategoryChanged(id)
            }
        }

        vm.categories.observe(viewLifecycleOwner) {
            categoryListAdapter.submitList(it)
        }
    }
}