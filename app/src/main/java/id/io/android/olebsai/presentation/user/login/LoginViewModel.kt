package id.io.android.olebsai.presentation.user.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
class LoginViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private var username = ""
    private var password = ""

    private val _isEmptyForm = MutableLiveData(false)
    val isEmptyForm: LiveData<Boolean>
        get() = _isEmptyForm

    private val _isErrorForm = MutableLiveData(false)
    val isErrorForm: LiveData<Boolean>
        get() = _isErrorForm

    private val _loginResult = SingleLiveEvent<LoadState<Pair<User, String>>>()
    val loginResult: LiveData<LoadState<Pair<User, String>>>
        get() = _loginResult

    fun onUsernameChanged(username: String) {
        this.username = username
        _isEmptyForm.value = false
        _isErrorForm.value = false
    }

    fun onPasswordChanged(password: String) {
        this.password = password
        _isEmptyForm.value = false
        _isErrorForm.value = false
    }

    fun login() {
        if (username.isEmpty() || password.isEmpty()) {
            _isEmptyForm.value = true
            return
        }

        _loginResult.value = LoadState.Loading
        viewModelScope.launch {
            val result = repository.login(
                LoginParams(
                    email = username,
                    password = password
                )
            )
            if (result is LoadState.Success) {
                repository.saveUser(result.data.first)
                repository.setLoggedIn(true)
                repository.setToken(result.data.second)
            }
            _loginResult.value = result
        }
    }

    fun setLoggedIn() {
        repository.setLoggedIn(true)
    }
}