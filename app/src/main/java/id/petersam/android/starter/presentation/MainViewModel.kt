package id.petersam.android.starter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.petersam.android.starter.domain.model.User
import id.petersam.android.starter.domain.usecase.UserUseCases
import id.petersam.android.starter.util.LoadState
import id.petersam.android.starter.util.NoParams
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userUseCases: UserUseCases) : ViewModel() {

    private val _user = MutableLiveData<LoadState<User?>>()
    val user: LiveData<LoadState<User?>>
        get() = _user

    init {
        getUser()
    }

    private fun getUser() {
        _user.value = LoadState.Loading
        viewModelScope.launch {
            _user.value = userUseCases.getUserUseCase(NoParams)
        }
    }
}