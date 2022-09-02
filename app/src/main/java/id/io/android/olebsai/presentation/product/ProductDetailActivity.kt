package id.io.android.olebsai.presentation.product

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import coil.load
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.io.android.olebsai.R
import id.io.android.olebsai.core.BaseActivity
import id.io.android.olebsai.databinding.ActivityProductDetailBinding
import id.io.android.olebsai.domain.model.product.Product
import id.io.android.olebsai.util.toRupiah
import id.io.android.olebsai.util.viewBinding

@AndroidEntryPoint
class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding, ProductDetailViewModel>() {

    override val binding by viewBinding(ActivityProductDetailBinding::inflate)
    override val vm: ProductDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        setupActionView()
        observeViewModel()
    }

    private fun setupView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun setupActionView() {
        binding.btnAddToCart.setOnClickListener {
            vm.addProductToBasket()
        }
    }

    private fun observeViewModel() {
        vm.product.observe(
            onLoading = {
                binding.pbLoading.isVisible = true
            },
            onSuccess = {
                binding.pbLoading.isVisible = false
                inflateProductData(it)
            },
            onError = {
                binding.pbLoading.isVisible = false
            }
        )

        vm.insertProduct.observe(
            onLoading = {
                binding.pbLoading.isVisible = true
            },
            onSuccess = {
                binding.pbLoading.isVisible = false
                Snackbar.make(
                    binding.root,
                    getString(R.string.basket_success_add_product_to_basket),
                    Snackbar.LENGTH_SHORT
                ).show()
            },
            onError = {
                binding.pbLoading.isVisible = false
            }
        )
    }

    private fun inflateProductData(product: Product?) {
        product?.let {
            with(binding) {
                imgProduct.load(product.imageUrl) {
                    placeholder(R.color.background)
                }
                tvName.text = product.name
                tvPrice.text = product.price.toRupiah()
                tvOriginalPrice.text = product.originalPrice.toRupiah()
                tvPercentDiscount.text = "${product.percentDiscount}%"
                tvRating.text = product.rating.toString()
                tvSoldCount.text = "Terjual ${product.soldCount}"

                tvShopName.text = product.shopName
                tvCondition.text = product.condition
                tvDimension.text = product.dimension
                tvMinOrder.text = product.minOrder
                tvCategory.text = product.category
                tvDesc.text = product.description
            }
        }
    }
}