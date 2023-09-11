package id.io.android.olebsai.presentation.account.update

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.model.user.RegisterParams
import id.io.android.olebsai.domain.repository.UserRepository
import id.io.android.olebsai.util.LoadState
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _updateProfileResult = MutableLiveData<LoadState<Any>>()
    val updateProfileResult: LiveData<LoadState<Any>>
        get() = _updateProfileResult

    fun updateProfile(registerParams: RegisterParams) {
        _updateProfileResult.value = LoadState.Loading
        viewModelScope.launch {
            _updateProfileResult.value = userRepository.updateProfile(registerParams)
        }
    }
}