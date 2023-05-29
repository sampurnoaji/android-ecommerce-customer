package id.io.android.olebsai.presentation.category

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    //    private val categoryListAdapter by lazy {
//        CategoryListVerticalAdapter(object : CategoryListVerticalAdapter.Listener {
//            override fun onCategoryClicked(id: Int) {
//                vm.onCategoryChanged(id)
//            }
//        })
//    }
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
        setupSearchView()
//        setupCategoryList()
        setupProductList()
        observeViewModel()
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewLifecycleOwner.lifecycleScope.launch {
                    vm.getSearchProductStream(query.orEmpty()).collectLatest {
                        productListAdapter.submitData(it)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        vm.getSearchProductStream("").collectLatest {
                            productListAdapter.submitData(it)
                        }
                    }
                }
                return false
            }
        })
    }

//    private fun setupCategoryList() {
//        binding.rvCategory.apply {
//            adapter = categoryListAdapter
//            layoutManager =
//                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        }
//    }

    private fun setupProductList() {
        binding.rvProduct.apply {
            adapter = productListAdapter
            layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
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
                vm.onCategoryChanged(id)
            }
        }

//        vm.categories.observe(viewLifecycleOwner) {
//            categoryListAdapter.submitList(it)
//        }
    }
}