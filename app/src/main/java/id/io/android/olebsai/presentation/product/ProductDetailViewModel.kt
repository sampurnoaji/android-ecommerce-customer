package id.io.android.olebsai.presentation.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.model.product.Product
import id.io.android.olebsai.domain.usecase.product.ProductUseCases
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.NoParams
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productUseCases: ProductUseCases
) : ViewModel() {

    private val _product = MutableLiveData<LoadState<Product>>()
    val product: LiveData<LoadState<Product>>
        get() = _product

    private val _insertProduct = MutableLiveData<LoadState<Boolean>>()
    val insertProduct: LiveData<LoadState<Boolean>>
        get() = _insertProduct

    private val _basketProducts = MutableLiveData<List<Product>>()
    val basketProducts: LiveData<List<Product>>
        get() = _basketProducts

    init {
        getBasketProducts()
    }

    fun getProductDetail(productId: Int) {
        _product.value = LoadState.Loading
        viewModelScope.launch {
            _product.value = productUseCases.getProductDetailUseCase(productId)
        }
    }

    fun addProductToBasket() {
        if (_product.value is LoadState.Success) {
            (_product.value as LoadState.Success<Product>).data?.let {
                _insertProduct.value = LoadState.Loading
                viewModelScope.launch {
                    try {
                        productUseCases.insertProductToBasketUseCase(it)
                        _insertProduct.value = LoadState.Success(true)
                    } catch (e: Exception) {
                        _insertProduct.value = LoadState.Error()
                    }
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