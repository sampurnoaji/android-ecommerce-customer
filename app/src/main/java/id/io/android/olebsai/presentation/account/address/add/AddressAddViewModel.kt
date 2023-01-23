package id.io.android.olebsai.presentation.account.address.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.model.address.Address
import id.io.android.olebsai.domain.repository.AddressRepository
import id.io.android.olebsai.util.LoadState
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AddressAddViewModel @Inject constructor(
    private val repository: AddressRepository
) : ViewModel() {

    private val _addAddressResult = MutableLiveData<LoadState<Any>>()
    val addAddressResult: LiveData<LoadState<Any>>
        get() = _addAddressResult

    private val _updateAddressResult = MutableLiveData<LoadState<Any>>()
    val updateAddressResult: LiveData<LoadState<Any>>
        get() = _updateAddressResult

    private val _deleteAddressResult = MutableLiveData<LoadState<Any>>()
    val deleteAddressResult: LiveData<LoadState<Any>>
        get() = _deleteAddressResult

    fun addAddress(address: Address) {
        _addAddressResult.value = LoadState.Loading
        viewModelScope.launch {
            _addAddressResult.value = repository.addAddress(address)
        }
    }

    fun updateAddress(address: Address) {
        _updateAddressResult.value = LoadState.Loading
        viewModelScope.launch {
            _updateAddressResult.value = repository.updateAddress(address)
        }
    }

    fun deleteAddress(addressId: Int) {
        _deleteAddressResult.value = LoadState.Loading
        viewModelScope.launch {
            _deleteAddressResult.value = repository.deleteAddress(addressId)
        }
    }
}