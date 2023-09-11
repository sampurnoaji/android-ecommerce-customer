package id.io.android.olebsai.presentation.order.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseActivity
import id.io.android.olebsai.databinding.ActivityOrderDetailBinding
import id.io.android.olebsai.domain.model.order.Order
import id.io.android.olebsai.domain.model.order.Order.Status
import id.io.android.olebsai.domain.model.order.Order.Status.BELUM_BAYAR
import id.io.android.olebsai.domain.model.order.Order.Status.DITERIMA
import id.io.android.olebsai.presentation.order.history.OrderViewModel
import id.io.android.olebsai.presentation.order.history.setValue
import id.io.android.olebsai.presentation.order.success.SuccessActivity
import id.io.android.olebsai.presentation.order.success.SuccessActivity.Type.ORDER_FINISH
import id.io.android.olebsai.presentation.order.success.SuccessActivity.Type.PAYMENT
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class OrderDetailActivity : BaseActivity<ActivityOrderDetailBinding, OrderViewModel>() {

    override val binding by viewBinding(ActivityOrderDetailBinding::inflate)
    override val vm: OrderViewModel by viewModels()

    private val productListAdapter by lazy { OrderedProductListAdapter() }

    private var headerId = ""

    companion object {
        private const val KEY_HEADER_ID = "headerId"

        @JvmStatic
        fun start(context: Context, headerId: String) {
            val starter = Intent(context, OrderDetailActivity::class.java)
                .putExtra(KEY_HEADER_ID, headerId)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()

        headerId = intent.getStringExtra(KEY_HEADER_ID).orEmpty()
        vm.getOrderDetail(headerId)

        observeViewModel()
    }

    private fun setupView() {
        with(binding.toolbar) {
            imgBack.setOnClickListener { finish() }
            tvTitle.text = getString(R.string.order)
        }

        with(binding.rvOrderedProduct) {
            adapter = productListAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@OrderDetailActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun observeViewModel() {
        vm.orderDetailResult.observe(
            onSuccess = {
                it?.order?.let { order -> setOrder(order) }
                productListAdapter.submitList(it?.orderedProducts)
            },
            onError = {}
        )

        vm.payOrderResult.observe(
            onSuccess = {
                SuccessActivity.start(this, launcher, PAYMENT)
            },
            onError = {
                showInfoDialog(getString(R.string.order_pay_failed))
            }
        )

        vm.finishOrderResult.observe(
            onSuccess = {
                SuccessActivity.start(this, launcher, ORDER_FINISH)
            },
            onError = {
                showInfoDialog(getString(R.string.order_finish_failed))
            }
        )
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                vm.getOrderDetail(headerId)
            }
        }

    private fun setOrder(order: Order) {
        binding.sectionOrder.setValue(order)
        setupButtonAction(order.status, order.headerId)
    }

    private fun setupButtonAction(status: Status, headerId: String) {
        binding.sectionButton.isVisible = status == BELUM_BAYAR
        with(binding.btnAction) {
            when (status) {
                BELUM_BAYAR -> {
                    text = getString(R.string.pay)
                    setOnClickListener { vm.payOrder(headerId) }
                }
                DITERIMA -> {
                    text = getString(R.string.finish)
                    setOnClickListener { vm.finishOrder(headerId) }
                }
                else -> {}
            }
        }
    }
}