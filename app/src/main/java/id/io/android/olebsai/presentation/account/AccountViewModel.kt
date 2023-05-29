package id.io.android.olebsai.presentation.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.model.address.Address
import id.io.android.olebsai.domain.model.user.User
import id.io.android.olebsai.domain.repository.UserRepository
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.SingleLiveEvent
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AccountViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val _masterUserResult = SingleLiveEvent<LoadState<User>>()
    val masterUserResult: LiveData<LoadState<User>>
        get() = _masterUserResult

    fun getUser(): User? = userRepository.getUser()

    fun getMasterUser() {
        _masterUserResult.value = LoadState.Loading
        viewModelScope.launch {
            _masterUserResult.value = userRepository.getMasterUser()
        }
    }

    suspend fun getDefaultAddress(): Address? = userRepository.getDefaultAddress()
}