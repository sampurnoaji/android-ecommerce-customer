package id.petersam.android.starter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.petersam.android.starter.domain.model.User
import id.petersam.android.starter.domain.usecase.UserUseCases
import id.petersam.android.starter.util.LoadState
import id.petersam.android.starter.util.LoadStateUtil
import id.petersam.android.starter.util.NoParams
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userUseCases: UserUseCases) : ViewModel() {
    val user: LiveData<LoadState<User>> = liveData {
        emit(LoadState.Loading)
        emit(LoadStateUtil.map(userUseCases.getUserUseCase(NoParams)))
    }
}