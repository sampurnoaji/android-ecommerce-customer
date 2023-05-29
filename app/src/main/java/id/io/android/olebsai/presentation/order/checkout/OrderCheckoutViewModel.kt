package id.io.android.olebsai.presentation.order.checkout

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.model.address.Address
import id.io.android.olebsai.domain.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class OrderCheckoutViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    suspend fun getDefaultAddress(): Address? = userRepository.getDefaultAddress()
}