package id.io.android.olebsai.presentation.product.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isGone
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseActivity
import id.io.android.olebsai.databinding.ActivityProductDetailBinding
import id.io.android.olebsai.domain.model.product.WProduct
import id.io.android.olebsai.presentation.user.login.LoginActivity
import id.io.android.olebsai.util.toRupiah
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding, ProductDetailViewModel>() {

    override val binding by viewBinding(ActivityProductDetailBinding::inflate)
    override val vm: ProductDetailViewModel by viewModels()

    private var basketCountView: TextView? = null

    companion object {
        private const val KEY_PRODUCT_ID = "product-id"

        @JvmStatic
        fun start(context: Context, productId: String) {
            val starter = Intent(context, ProductDetailActivity::class.java)
                .putExtra(KEY_PRODUCT_ID, productId)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        setupActionView()
        observeViewModel()

        intent?.getStringExtra(KEY_PRODUCT_ID)?.let {
            vm.getProductDetail(it)
        }
    }

    private fun setupView() {
        with(binding.toolbar) {
            imgBack.setOnClickListener { finish() }
            tvTitle.text = getString(R.string.product_detail)
        }
    }

    private fun setupActionView() {
        binding.btnAddToCart.setOnClickListener {
            if (vm.checkLoggedInStatus()) {
                vm.addProductToBasket(
                    catatan = "",
                    qty = 1
                )
            } else {
                launcher.launch(Intent(this, LoginActivity::class.java))
            }
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_product_detail, menu)
        basketCountView = menu?.findItem(R.id.menuBasket)?.actionView?.findViewById(R.id.tvCount)
        observeBasketProducts()
        return super.onCreateOptionsMenu(menu)
    }

    private fun observeViewModel() {
        vm.product.observe(
            onLoading = {},
            onSuccess = { product ->
                product?.let { inflateProductData(it) }
            },
            onError = {
                showInfoDialog(it?.message.toString())
            }
        )

        vm.insertProduct.observe(
            onLoading = {},
            onSuccess = {
                showInfoDialog(getString(R.string.basket_add_product_to_basket_success))
            },
            onError = {
                showInfoDialog(
                    it?.message ?: getString(R.string.basket_add_product_to_basket_failed)
                )
            }
        )
    }

    private fun observeBasketProducts() {
        vm.basketProducts.observe(this) {
            setBasketProductsCountBadge(it.size)
        }
    }

    private fun inflateProductData(product: WProduct) {
        with(binding) {
//            imgProduct.load(product.imageUrl) {
//                placeholder(R.color.background)
//            }
            tvName.text = product.namaProduk
            if (product.isHargaPromo) {
                tvPrice.text = product.hargaPromo.toRupiah()
                tvOriginalPrice.text = product.hargaNormal.toRupiah()
                tvPercentDiscount.text = "${product.discount()}%"
            } else {
                tvPrice.text = product.hargaNormal.toRupiah()
                tvOriginalPrice.isGone = true
                tvPercentDiscount.isGone = true
            }
//            tvRating.text = product.rating.toString()
            tvSoldCount.text = "Terjual ${product.qtyTerjual}"

            tvShopName.text = product.namaToko
//            tvCondition.text = "Baru"
//            tvDimension.text = product.dimension
//            tvMinOrder.text = product.minOrder
            tvCategory.text = product.namaKategori
            tvSubCategory.text = product.namaSubKategori
            tvDesc.text = product.deskripsi
        }
    }

    private fun setBasketProductsCountBadge(count: Int) {
        basketCountView?.text = count.toString()
    }
}