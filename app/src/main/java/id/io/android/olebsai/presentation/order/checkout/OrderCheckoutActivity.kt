package id.io.android.olebsai.presentation.order.checkout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.databinding.ActivityOrderCheckoutBinding
import id.io.android.olebsai.domain.model.basket.BasketItem
import id.io.android.olebsai.presentation.account.address.list.AddressListActivity
import id.io.android.olebsai.presentation.order.processed.OrderProcessedActivity
import id.io.android.olebsai.util.toRupiah
import id.io.android.olebsai.util.viewBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderCheckoutActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityOrderCheckoutBinding::inflate)
    private val vm by viewModels<OrderCheckoutViewModel>()

    private val productCheckoutListAdapter by lazy { ProductCheckoutListAdapter() }

    companion object {
        private const val KEY_PRODUCTS = "products"

        @JvmStatic
        fun start(context: Context, items: ArrayList<BasketItem>) {
            val starter = Intent(context, OrderCheckoutActivity::class.java)
                .putParcelableArrayListExtra(KEY_PRODUCTS, items)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
        setupActionView()
        getDefaultAddress()
    }

    private fun setupView() {
        with(binding.toolbar) {
            imgBack.setOnClickListener { finish() }
            tvTitle.text = getString(R.string.checkout_summary)
        }

        //TODO nama toko
        binding.tvShopName.text = "Nama Toko"

        with(binding.rvProduct) {
            adapter = productCheckoutListAdapter
            layoutManager = LinearLayoutManager(this@OrderCheckoutActivity)
        }

        intent?.getParcelableArrayListExtra<BasketItem>(KEY_PRODUCTS)?.let { items ->
            productCheckoutListAdapter.submitList(items)

            val productTotal = items.sumOf {
                it.qtyPembelian * (if (it.product.isHargaPromo) it.product.hargaPromo else it.product.hargaNormal)
            }
            binding.tvProductTotal.text = productTotal.toRupiah()
            binding.tvDeliveryTotal.text = "Rp 0"
            binding.tvSummaryTotal.text = productTotal.toRupiah()
        }
    }

    private fun setupActionView() {
        binding.tvAddressSelect.setOnClickListener {
            AddressListActivity.start(this)
        }

        binding.btnPay.setOnClickListener {
            OrderProcessedActivity.start(this)
        }
    }

    private fun getDefaultAddress() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.getDefaultAddress().let {
                    with(binding) {
                        tvAddressLabel.text = "${it?.label.orEmpty()} - ${it?.name.orEmpty()}"
                        tvAddress.text = it?.address
                        tvAddressNote.text = it?.note
                        tvPhone.text = it?.phone
                    }
                }
            }
        }
    }
}