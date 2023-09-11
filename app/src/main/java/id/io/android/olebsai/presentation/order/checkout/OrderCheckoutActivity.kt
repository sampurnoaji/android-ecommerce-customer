package id.io.android.olebsai.presentation.order.checkout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseActivity
import id.io.android.olebsai.databinding.ActivityOrderCheckoutBinding
import id.io.android.olebsai.domain.model.basket.BasketItem
import id.io.android.olebsai.domain.model.basket.Courier
import id.io.android.olebsai.domain.model.user.User
import id.io.android.olebsai.presentation.account.AccountViewModel
import id.io.android.olebsai.presentation.basket.BasketViewModel
import id.io.android.olebsai.presentation.courier.CourierListDialogFragment
import id.io.android.olebsai.presentation.courier.CourierListDialogFragment.Item
import id.io.android.olebsai.presentation.courier.CourierListDialogFragment.Listener
import id.io.android.olebsai.presentation.order.success.SuccessActivity
import id.io.android.olebsai.presentation.order.success.SuccessActivity.Type.CHECKOUT
import id.io.android.olebsai.util.obtainParcelableArrayList
import id.io.android.olebsai.util.toRupiah
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class OrderCheckoutActivity : BaseActivity<ActivityOrderCheckoutBinding, OrderCheckoutViewModel>() {

    override val binding by viewBinding(ActivityOrderCheckoutBinding::inflate)
    override val vm by viewModels<OrderCheckoutViewModel>()

    private val accountViewModel: AccountViewModel by viewModels()
    private val basketViewModel: BasketViewModel by viewModels()

    private val productCheckoutListAdapter by lazy { ProductCheckoutListAdapter() }

    private val basketItems = mutableListOf<BasketItem>()

    private var productTotal: Long? = null
    private var selectedCourierServiceItem: Item? = null

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
        observeViewModel()
        accountViewModel.getUser()
    }

    private fun setupView() {
        with(binding.toolbar) {
            imgBack.setOnClickListener { finish() }
            tvTitle.text = getString(R.string.checkout_summary)
        }

        with(binding.rvProduct) {
            adapter = productCheckoutListAdapter
            layoutManager = LinearLayoutManager(this@OrderCheckoutActivity)
        }

        intent?.obtainParcelableArrayList<BasketItem>(KEY_PRODUCTS)?.let { items ->
            basketItems.apply {
                if (isNotEmpty()) clear()
                addAll(items)
            }
            productCheckoutListAdapter.submitList(items)

            productTotal = items.sumOf {
                it.qtyPembelian * (if (it.product.isHargaPromo) it.product.hargaPromo else it.product.hargaNormal)
            }
            binding.tvProductTotal.text = productTotal?.toRupiah()
            binding.tvDeliveryTotal.text = "-"
            binding.tvSummaryTotal.text = "-"
        }
    }

    private fun setupActionView() {
        with(binding) {
            btnBuy.setOnClickListener {
                basketViewModel.checkout(
                    basketIds = basketItems.map { it.keranjangId },
                    namaJasaPengiriman = selectedCourierServiceItem?.name.orEmpty(),
                    servisJasaPengiriman = selectedCourierServiceItem?.service?.namaServis.orEmpty(),
                    ongkir = selectedCourierServiceItem?.service?.ongkir ?: 0L,
                    estimasiSampai = selectedCourierServiceItem?.service?.estimasiSampai.orEmpty(),
                )
            }

            containerCourier.setOnClickListener {
                if (basketItems.isNotEmpty())
                    basketViewModel.checkOngkir(basketItems.map { it.keranjangId })
            }
        }
    }

    private fun observeViewModel() {
        accountViewModel.userResult.observe(
            onSuccess = {
                it?.let { inflateUser(it) }
            },
            onError = {
                accountViewModel.getUserCached()?.let { inflateUser(it) }
            }
        )

        basketViewModel.checkOngkirResult.observe(
            onSuccess = { result ->
                result?.let {
                    CourierListDialogFragment.newInstance(
                        it as ArrayList<Courier>,
                        object : Listener {
                            override fun onCourierSelected(item: Item) {
                                selectedCourierServiceItem = item
                                updateSummary()
                            }
                        })
                        .show(supportFragmentManager, OrderCheckoutActivity::class.java.simpleName)
                }
            },
            onError = {
                showInfoDialog(getString(R.string.checkout_courier_failed))
            }
        )

        basketViewModel.checkoutResult.observe(
            onSuccess = {
                SuccessActivity.start(this, launcher, CHECKOUT)
            },
            onError = {
                showInfoDialog(it?.message ?: getString(R.string.error_happened))
            }
        )
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    private fun inflateUser(user: User) {
        with(binding) {
            tvName.text = "${user.username} - ${user.phone}"
            tvAddressFull.text = user.address.alamat
            tvAddress.text =
                "${user.address.kecamatan}, ${user.address.kota}, ${user.address.provinsi}, ${user.address.kodePos}"
        }
    }

    private fun updateSummary() {
        with(binding) {
            tvCourierName.isVisible = selectedCourierServiceItem == null
            with(courier) {
                root.isVisible = selectedCourierServiceItem != null
                tvPrice.text = selectedCourierServiceItem?.service?.ongkir?.toRupiah()
                tvName.text = selectedCourierServiceItem?.name
                tvService.text = selectedCourierServiceItem?.service?.namaServis
                tvCourierEstimate.text =
                    "${selectedCourierServiceItem?.service?.estimasiSampai} hari"
            }
            tvDeliveryTotal.text = selectedCourierServiceItem?.service?.ongkir?.toRupiah()

            if (selectedCourierServiceItem?.service != null && productTotal != null) {
                val total = selectedCourierServiceItem?.service!!.ongkir + productTotal!!
                tvSummaryTotal.text = total.toRupiah()
            }

            btnBuy.isEnabled = selectedCourierServiceItem?.service != null && productTotal != null
        }
    }
}