package id.io.android.olebsai.presentation.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseFragment
import id.io.android.olebsai.databinding.FragmentHomeBinding
import id.io.android.olebsai.domain.model.category.Category
import id.io.android.olebsai.presentation.MainActivity
import id.io.android.olebsai.presentation.MainViewModel
import id.io.android.olebsai.presentation.category.CategoryFragment
import id.io.android.olebsai.presentation.home.adapter.BannerListAdapter
import id.io.android.olebsai.presentation.home.adapter.CategoryListAdapter
import id.io.android.olebsai.presentation.home.adapter.ProductListAdapter
import id.io.android.olebsai.presentation.home.adapter.ProductViewHolder
import id.io.android.olebsai.presentation.home.adapter.VoucherListAdapter
import id.io.android.olebsai.presentation.product.detail.ProductDetailActivity
import id.io.android.olebsai.util.dpToPx
import id.io.android.olebsai.util.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {
    override val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)
    override val vm: HomeViewModel by viewModels()
    private val actVm: MainViewModel by activityViewModels()

    private var carouselJob: Job? = null

    private val bannerListAdapter by lazy { BannerListAdapter(vm.images) }
    private val categoryListAdapter by lazy { CategoryListAdapter(vm.categories, categoryListener) }
    private val voucherListAdapter by lazy { VoucherListAdapter(vm.vouchers) }
    private val productListAdapter by lazy { ProductListAdapter(productListener) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionView()
        observeViewModel()
        vm.getFrontPageData()

        setupBannerCarousel(vm.images)
        setupCategoryList()
        setupVoucherList()
        setupProductList()
    }

    private fun setupActionView() {
        binding.toolbar.imgBack.isGone = true

        with(binding.swipeRefreshLayout) {
            setOnRefreshListener {
                vm.getFrontPageData()
                isRefreshing = false
            }
        }

        binding.btnLogin.setOnClickListener {
            (requireActivity() as MainActivity).navigateToLogin()
        }
    }

    private fun observeViewModel() {
        actVm.isLoggedIn.observe(viewLifecycleOwner) {
            binding.btnLogin.isVisible = !it
        }

        vm.frontPageData.observe(
            onLoading = {},
            onSuccess = {
                productListAdapter.submitList(it?.featuredProduk)
            },
            onError = {}
        )
    }

    private fun setupBannerCarousel(images: List<Int>) {
        binding.vpBanner.apply {
            adapter = bannerListAdapter
            offscreenPageLimit = 2

            val previewOffsetPx = 30.dpToPx(resources.displayMetrics)
            setPadding(previewOffsetPx, 0, previewOffsetPx, 0)

            val pageMarginPx = 8.dpToPx(resources.displayMetrics)
            val marginTransformer = MarginPageTransformer(pageMarginPx)
            setPageTransformer(marginTransformer)

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    carouselJob?.cancel()
                    carouselJob = lifecycleScope.launch(Dispatchers.Main) {
                        delay(3000)
                        currentItem =
                            if (position >= images.size - 1) 0
                            else position + 1
                    }
                }
            })

            TabLayoutMediator(binding.dotBanner, this) { _, _ -> }.attach()
            binding.tvBannerViewMore.isVisible = images.isNotEmpty()
        }
    }

    private fun setupCategoryList() {
        binding.rvCategory.apply {
            adapter = categoryListAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupVoucherList() {
        binding.rvVoucher.apply {
            adapter = voucherListAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupProductList() {
        binding.rvProduct.apply {
            adapter = productListAdapter
            layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
        }
    }

    private val categoryListener = object : CategoryListAdapter.Listener {
        override fun onCategoryClicked(category: Category) {
            if (actVm.isLoggedIn.value == false) {
                (requireActivity() as MainActivity).navigateToLogin()
                return
            }
            (requireActivity() as MainActivity).navigateToMenu(
                R.id.menuCategory,
                Bundle().apply {
                    putInt(CategoryFragment.CATEGORY_KEY, category.id)
                }
            )
        }
    }

    private val productListener = object : ProductViewHolder.Listener {
        override fun onProductClicked(id: String) {
            ProductDetailActivity.start(requireContext(), id)
        }
    }

    override fun onResume() {
        super.onResume()
        carouselJob?.start()
    }

    override fun onPause() {
        super.onPause()
        carouselJob?.cancel()
    }
}