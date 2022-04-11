package id.io.android.seller.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.seller.domain.model.User
import id.io.android.seller.domain.usecase.UserUseCases
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userUseCases: UserUseCases) : ViewModel() {
    val user: LiveData<User?> = liveData {
        emit(userUseCases.getUserUseCase())
    }
}