package id.io.android.olebsai.presentation.order

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.databinding.ActivityOrderCheckoutBinding
import id.io.android.olebsai.util.toRupiah
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class OrderCheckoutActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityOrderCheckoutBinding::inflate)
    private val vm by viewModels<OrderCheckoutViewModel>()

    private val productCheckoutListAdapter by lazy { ProductCheckoutListAdapter() }

    companion object {
        private const val KEY_PRODUCTS = "products"

        @JvmStatic
        fun start(context: Context, products: ArrayList<ProductCheckout>?) {
            val starter = Intent(context, OrderCheckoutActivity::class.java)
                .putParcelableArrayListExtra(KEY_PRODUCTS, products)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        with(binding.toolbar) {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            setNavigationOnClickListener { onBackPressed() }
        }

        with(binding.rvProduct) {
            adapter = productCheckoutListAdapter
            layoutManager = LinearLayoutManager(this@OrderCheckoutActivity)
        }

        intent?.getParcelableArrayListExtra<ProductCheckout>(KEY_PRODUCTS)?.let { products ->
            productCheckoutListAdapter.submitList(products)

            val productTotal = products.sumOf { it.total * it.qty }
            binding.tvProductTotal.text = productTotal.toRupiah()
            binding.tvDeliveryTotal.text = "Rp 0"
            binding.tvSummaryTotal.text = productTotal.toRupiah()
        }
    }
}