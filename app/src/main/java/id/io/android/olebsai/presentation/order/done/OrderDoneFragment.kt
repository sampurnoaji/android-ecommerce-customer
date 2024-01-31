package id.io.android.olebsai.presentation.order.done

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseFragment
import id.io.android.olebsai.databinding.FragmentOrderOngoingBinding
import id.io.android.olebsai.domain.model.order.Order
import id.io.android.olebsai.presentation.order.detail.OrderDetailActivity
import id.io.android.olebsai.presentation.order.done.OrderPagingAdapter.Listener
import id.io.android.olebsai.presentation.order.history.OrderViewModel
import id.io.android.olebsai.util.viewBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderDoneFragment :
    BaseFragment<FragmentOrderOngoingBinding, OrderViewModel>(R.layout.fragment_order_ongoing) {

    override val binding by viewBinding(FragmentOrderOngoingBinding::bind)
    override val vm: OrderViewModel by viewModels()

    private val orderPagingAdapter by lazy {
        OrderPagingAdapter(
            object : Listener {
                override fun onItemClicked(order: Order) {
                    OrderDetailActivity.start(requireContext(), order.headerId)
                }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observeViewModel()
    }

    private fun setupView() {
        with(binding.rvOrder) {
            adapter = orderPagingAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        with(binding.root) {
            setOnRefreshListener {
                orderPagingAdapter.refresh()
                isRefreshing = false
            }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.doneOrders.collect {
                    orderPagingAdapter.submitData(it)
                }
            }
        }
    }
}