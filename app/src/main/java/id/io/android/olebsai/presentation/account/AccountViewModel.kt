package id.io.android.olebsai.presentation.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.model.user.User
import id.io.android.olebsai.domain.repository.UserRepository
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.SingleLiveEvent
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AccountViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val _userResult = SingleLiveEvent<LoadState<User>>()
    val userResult: LiveData<LoadState<User>>
        get() = _userResult

    fun getUserCached(): User? = userRepository.getUserCached()

    fun getUser() {
        _userResult.value = LoadState.Loading
        viewModelScope.launch {
            _userResult.value = userRepository.getMasterUser()
        }
    }
}