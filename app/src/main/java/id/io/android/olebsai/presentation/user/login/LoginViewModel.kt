package id.io.android.olebsai.presentation.user.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.usecase.user.UserUseCases
import id.io.android.olebsai.util.LoadState
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userUseCases: UserUseCases) : ViewModel() {

    private var username = ""
    private var password = ""

    private val _isErrorForm = MutableLiveData(false)
    val isErrorForm: LiveData<Boolean>
        get() = _isErrorForm

    private val _login = MutableLiveData<LoadState<Boolean>>()
    val login: LiveData<LoadState<Boolean>>
        get() = _login

    fun onUsernameChanged(username: String) {
        this.username = username
        _isErrorForm.value = false
    }

    fun onPasswordChanged(password: String) {
        this.password = password
        _isErrorForm.value = false
    }

    fun login() {
        if (username.isEmpty() || password.isEmpty()) {
            _isErrorForm.value = true
            return
        }
        _login.value = LoadState.Success(true)
        userUseCases.setLoggedInUseCase(true)
    }
}