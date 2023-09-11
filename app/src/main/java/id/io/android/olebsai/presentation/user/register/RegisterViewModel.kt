package id.io.android.olebsai.presentation.user.register

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.model.user.ApiAddress
import id.io.android.olebsai.domain.model.user.RegisterParams
import id.io.android.olebsai.domain.model.user.User
import id.io.android.olebsai.domain.repository.UserRepository
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.SingleLiveEvent
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: UserRepository,
) : ViewModel() {

    var email = ""
    var name = ""
    var phoneNumber = ""
    var password = ""
    var repeatPassword = ""

    private val _register = SingleLiveEvent<LoadState<String>>()
    val register: LiveData<LoadState<String>>
        get() = _register

    fun onEmailChanged(email: String) {
        this.email = email.trim()
    }

    fun onNameChanged(name: String) {
        this.name = name.trim()
    }

    fun onPhoneNumberChanged(phoneNumber: String) {
        this.phoneNumber = phoneNumber
    }

    fun onPasswordChanged(password: String) {
        this.password = password.trim()
    }

    fun onRepeatPasswordChanged(password: String) {
        this.repeatPassword = password.trim()
    }

    fun register(
        address: Editable?,
        selectedProvince: ApiAddress?,
        selectedDistrict: ApiAddress?,
        selectedSubDistrict: ApiAddress?,
        postalCode: Editable?
    ) {
        _register.value = LoadState.Loading
        viewModelScope.launch {
            _register.value = repository.register(
                RegisterParams(
                    name = name,
                    email = email,
                    phoneNumber = phoneNumber,
                    password = password,
                    address = User.Address(
                        alamat = if (address.isNullOrBlank()) "" else address.toString(),
                        provinsi = selectedProvince?.name.orEmpty(),
                        provinsiId = selectedProvince?.id.orEmpty(),
                        kota = selectedDistrict?.name.orEmpty(),
                        kotaId = selectedDistrict?.id.orEmpty(),
                        kecamatan = selectedSubDistrict?.name.orEmpty(),
                        kecamatanId = selectedSubDistrict?.id.orEmpty(),
                        kodePos = if (postalCode.isNullOrBlank()) "" else postalCode.toString()
                    )
                )
            )
        }
    }
}