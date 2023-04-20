package id.io.android.olebsai.presentation.user.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.model.user.LoginParams
import id.io.android.olebsai.domain.model.user.User
import id.io.android.olebsai.domain.repository.UserRepository
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.SingleLiveEvent
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class OtpViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {

    private val _loginWithOtpResult = SingleLiveEvent<LoadState<String>>()
    val loginWithOtpResult: LiveData<LoadState<String>>
        get() = _loginWithOtpResult

    var otp = ""

    fun onOtpChanged(otp: String) {
        this.otp = otp
    }

    fun loginWithOtp(email: String) {
        _loginWithOtpResult.value = LoadState.Loading
        viewModelScope.launch {
            _loginWithOtpResult.value = repository.loginWithOtp(
                LoginParams(
                    email = email,
                    otp = otp
                )
            )
        }
    }

    fun saveUser(user: User, token: String) {
        repository.setLoggedIn(true)
        repository.setToken(token)
        repository.saveUser(user)
    }
}