package id.io.android.olebsai.presentation.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.model.product.Product
import id.io.android.olebsai.domain.usecase.product.ProductUseCases
import id.io.android.olebsai.util.ui.Selection
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderCheckoutViewModel @Inject constructor(
    private val productUseCases: ProductUseCases
) : ViewModel() {


}