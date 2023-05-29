package id.io.android.olebsai.presentation.product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.model.product.Product
import id.io.android.olebsai.domain.model.product.WProduct
import id.io.android.olebsai.domain.repository.BasketRepository
import id.io.android.olebsai.domain.repository.ProductRepository
import id.io.android.olebsai.domain.repository.UserRepository
import id.io.android.olebsai.domain.usecase.product.ProductUseCases
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.NoParams
import id.io.android.olebsai.util.SingleLiveEvent
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val productUseCases: ProductUseCases,
    private val basketRepository: BasketRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _product = SingleLiveEvent<LoadState<WProduct>>()
    val product: LiveData<LoadState<WProduct>>
        get() = _product

    private val _insertProduct = SingleLiveEvent<LoadState<String>>()
    val insertProduct: LiveData<LoadState<String>>
        get() = _insertProduct

    private val _basketProducts = MutableLiveData<List<Product>>()
    val basketProducts: LiveData<List<Product>>
        get() = _basketProducts

    init {
        getBasketProducts()
    }

    fun checkLoggedInStatus(): Boolean = userRepository.isLoggedIn()

    fun getProductDetail(productId: String) {
        _product.value = LoadState.Loading
        viewModelScope.launch {
            _product.value = repository.getProductDetail(productId)
        }
    }

    fun addProductToBasket(
        qty: Int,
        catatan: String,
    ) {
        if (_product.value is LoadState.Success) {
            (_product.value as LoadState.Success<WProduct>).data.let {
                _insertProduct.value = LoadState.Loading
                viewModelScope.launch {
                    _insertProduct.value = basketRepository.addProductToBasket(
                        productId = it.produkId,
                        qty = qty,
                        tokoId = it.tokoId,
                        catatan = catatan
                    )
                }
            }
        }
    }

    private fun getBasketProducts() {
        viewModelScope.launch {
            productUseCases.getBasketProductsUseCase(NoParams).collect {
                _basketProducts.value = it
            }
        }
    }
}