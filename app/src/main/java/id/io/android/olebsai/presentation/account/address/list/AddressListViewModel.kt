package id.io.android.olebsai.presentation.account.address.list

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
class AddressListViewModel @Inject constructor(
    private val repository: AddressRepository,
) : ViewModel() {

    private val _addressListResult = MutableLiveData<LoadState<List<Address>>>()
    val addressListResult: LiveData<LoadState<List<Address>>>
        get() = _addressListResult

    private val _setAddressDefaultResult = MutableLiveData<LoadState<Any>>()
    val setAddressDefaultResult: LiveData<LoadState<Any>>
        get() = _setAddressDefaultResult

    init {
        getAddressList()
    }

    fun getAddressList() {
        _addressListResult.value = LoadState.Loading
        viewModelScope.launch {
            _addressListResult.value = repository.getAddressList()
        }
    }

    fun setAddressDefault(addressId: Int) {
        _setAddressDefaultResult.value = LoadState.Loading
        viewModelScope.launch {
            _setAddressDefaultResult.value = repository.setAddressDefault(addressId)
        }
    }
}