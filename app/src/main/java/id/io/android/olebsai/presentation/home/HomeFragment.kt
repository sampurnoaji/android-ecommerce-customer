package id.io.android.olebsai.presentation.home

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseFragment
import id.io.android.olebsai.databinding.FragmentHomeBinding
import id.io.android.olebsai.util.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {
    override val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)
    override val vm: HomeViewModel by viewModels()

    private var carouselJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBannerCarousel(vm.images)
    }

    private fun setupBannerCarousel(images: List<Int>) {
        binding.vpBanner.apply {
            adapter = BannerListAdapter(images)
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

    private fun Int.dpToPx(displayMetrics: DisplayMetrics): Int =
        (this * displayMetrics.density).toInt()

    override fun onResume() {
        super.onResume()
        carouselJob?.start()
    }

    override fun onPause() {
        super.onPause()
        carouselJob?.cancel()
    }
}