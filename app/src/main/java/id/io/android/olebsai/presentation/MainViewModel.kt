package id.io.android.olebsai.presentation

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.model.product.Product
import id.io.android.olebsai.domain.usecase.product.ProductUseCases
import id.io.android.olebsai.domain.usecase.user.UserUseCases
import id.io.android.olebsai.util.NoParams
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val productUseCases: ProductUseCases
) : ViewModel() {

    private val _isLoggedIn = MutableLiveData(false)
    val isLoggedIn: LiveData<Boolean>
        get() = _isLoggedIn

    private val _navigationBundle = MutableLiveData<Bundle?>()
    val navigationBundle: LiveData<Bundle?>
        get() = _navigationBundle

    private val _basketProducts = MutableLiveData<List<Product>>()
    val basketProducts: LiveData<List<Product>>
        get() = _basketProducts

    init {
        checkLoggedInStatus()
        getBasketProducts()
    }

    fun addBundleToNavigation(args: Bundle) {
        _navigationBundle.value = args
    }

    private fun getBasketProducts() {
        viewModelScope.launch {
            productUseCases.getBasketProductsUseCase(NoParams).collect {
                _basketProducts.value = it
            }
        }
    }

    fun checkLoggedInStatus() {
        _isLoggedIn.value = userUseCases.checkLoggedInUseCase()
    }

    fun login() {
        userUseCases.setLoggedInUseCase(true)
    }

    fun logout() {
        userUseCases.setLoggedInUseCase(false)
    }
}