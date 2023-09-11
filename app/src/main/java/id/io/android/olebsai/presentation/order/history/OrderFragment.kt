package id.io.android.olebsai.presentation.order.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseFragment
import id.io.android.olebsai.databinding.FragmentOrderBinding
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class OrderFragment : BaseFragment<FragmentOrderBinding, OrderViewModel>(R.layout.fragment_order) {

    override val binding by viewBinding(FragmentOrderBinding::bind)
    override val vm: OrderViewModel by viewModels()

    private val pagerAdapter by lazy { OrderPagerAdapter(childFragmentManager, lifecycle) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        with(binding) {
            toggleGroup.addOnButtonCheckedListener { _, _, _ ->
                pager.currentItem = if (btnOngoing.isChecked) 0 else 1
            }
        }

        with(binding.pager) {
            adapter = pagerAdapter
        }
    }
}