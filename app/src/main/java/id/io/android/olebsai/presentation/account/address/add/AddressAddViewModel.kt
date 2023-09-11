package id.io.android.olebsai.presentation.account.address.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.model.address.Address
import id.io.android.olebsai.domain.model.user.ApiAddress
import id.io.android.olebsai.domain.repository.UserRepository
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.SingleLiveEvent
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AddressAddViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _addAddressResult = SingleLiveEvent<LoadState<Any>>()
    val addAddressResult: LiveData<LoadState<Any>>
        get() = _addAddressResult

    private val _updateAddressResult = MutableLiveData<LoadState<Any>>()
    val updateAddressResult: LiveData<LoadState<Any>>
        get() = _updateAddressResult

    private val _deleteAddressResult = MutableLiveData<LoadState<Any>>()
    val deleteAddressResult: LiveData<LoadState<Any>>
        get() = _deleteAddressResult

    private val _provincesResult = MutableLiveData<LoadState<List<ApiAddress>>>()
    val provincesResult: LiveData<LoadState<List<ApiAddress>>>
        get() = _provincesResult

    private val _districtsResult = MutableLiveData<LoadState<List<ApiAddress>>>()
    val districtsResult: LiveData<LoadState<List<ApiAddress>>>
        get() = _districtsResult

    private val _subDistrictsResult = MutableLiveData<LoadState<List<ApiAddress>>>()
    val subDistrictsResult: LiveData<LoadState<List<ApiAddress>>>
        get() = _subDistrictsResult

    fun addAddress(address: Address) {
        _addAddressResult.value = LoadState.Loading
        viewModelScope.launch {
            try {
                userRepository.addAddress(address)
                _addAddressResult.value = LoadState.Success(Any())
            } catch (e: Exception) {
                _addAddressResult.value = LoadState.Error(message = e.message)
            }
        }
    }

    fun updateAddress(address: Address) {
        _updateAddressResult.value = LoadState.Loading
        viewModelScope.launch {
            try {
                userRepository.updateAddress(address)
                _updateAddressResult.value = LoadState.Success(Any())
            } catch (e: Exception) {
                _updateAddressResult.value = LoadState.Error(message = e.message)
            }
        }
    }

    fun deleteAddress(addressId: Int) {
        _deleteAddressResult.value = LoadState.Loading
        viewModelScope.launch {
            try {
                userRepository.deleteAddress(addressId)
                _deleteAddressResult.value = LoadState.Success(Any())
            } catch (e: Exception) {
                _deleteAddressResult.value = LoadState.Error(message = e.message)
            }
        }
    }

    fun getProvinces() {
        _provincesResult.value = LoadState.Loading
        viewModelScope.launch {
            _provincesResult.value = userRepository.getProvinces()
        }
    }

    fun getDistricts(provinceId: String) {
        _districtsResult.value = LoadState.Loading
        viewModelScope.launch {
            _districtsResult.value = userRepository.getDistricts(provinceId)
        }
    }

    fun getSubDistricts(districtId: String) {
        _subDistrictsResult.value = LoadState.Loading
        viewModelScope.launch {
            _subDistrictsResult.value = userRepository.getSubDistricts(districtId)
        }
    }
}