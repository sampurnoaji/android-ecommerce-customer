package id.io.android.olebsai.presentation.order.ongoing

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseFragment
import id.io.android.olebsai.databinding.FragmentOrderOngoingBinding
import id.io.android.olebsai.domain.model.order.Order
import id.io.android.olebsai.presentation.order.detail.OrderDetailActivity
import id.io.android.olebsai.presentation.order.history.OrderListAdapter
import id.io.android.olebsai.presentation.order.history.OrderListAdapter.Listener
import id.io.android.olebsai.presentation.order.history.OrderViewModel
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class OrderOngoingFragment :
    BaseFragment<FragmentOrderOngoingBinding, OrderViewModel>(R.layout.fragment_order_ongoing) {

    override val binding by viewBinding(FragmentOrderOngoingBinding::bind)
    override val vm: OrderViewModel by viewModels()

    private val orderListAdapter by lazy {
        OrderListAdapter(
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

        vm.getActiveOrders()
    }

    private fun setupView() {
        with(binding.rvOrder) {
            adapter = orderListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        with(binding.root) {
            setOnRefreshListener {
                vm.getActiveOrders()
                isRefreshing = false
            }
        }
    }

    private fun observeViewModel() {
        vm.activeOrdersResult.observe(
            onSuccess = { result ->
                with(binding) {
                    groupEmpty.isVisible = result.isNullOrEmpty()
                    rvOrder.isGone = result.isNullOrEmpty()
                    orderListAdapter.submitList(result)
                }
            },
            onError = {
                with(binding) {
                    groupEmpty.isVisible = true
                    rvOrder.isGone = true
                }
            }
        )
    }
}