package id.io.android.olebsai.presentation.account.address.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.model.address.Address
import id.io.android.olebsai.domain.repository.UserRepository
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.SingleLiveEvent
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AddressListViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    val addressListResult = userRepository.getAddressList()

    private val _setAddressDefaultResult = SingleLiveEvent<LoadState<Any>>()
    val setAddressDefaultResult: LiveData<LoadState<Any>>
        get() = _setAddressDefaultResult

    fun setAddressDefault(address: Address) {
        _setAddressDefaultResult.value = LoadState.Loading
        viewModelScope.launch {
            try {
                userRepository.getDefaultAddress()?.let {
                    userRepository.setDefaultAddress(it.copy(isDefault = false))
                }
                userRepository.setDefaultAddress(address.copy(isDefault = true))
                _setAddressDefaultResult.value = LoadState.Success(Any())
            } catch (e: Exception) {
                _setAddressDefaultResult.value = LoadState.Error(message = e.message)
            }
        }
    }
}