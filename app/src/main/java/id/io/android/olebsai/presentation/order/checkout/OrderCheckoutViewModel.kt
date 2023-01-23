package id.io.android.olebsai.presentation.order.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.model.address.Address
import javax.inject.Inject

@HiltViewModel
class OrderCheckoutViewModel @Inject constructor() : ViewModel() {

    private val _address = MutableLiveData<Address>()
    val address: LiveData<Address>
        get() = _address

    fun updateAddress(address: Address) {
        _address.value = address
    }
}