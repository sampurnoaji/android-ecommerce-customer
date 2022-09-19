package id.io.android.olebsai.presentation.account.address.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.model.address.Address
import id.io.android.olebsai.util.ui.Selection
import javax.inject.Inject

@HiltViewModel
class AddressListViewModel @Inject constructor() : ViewModel() {

    private val _address = MutableLiveData<List<Selection<Address>>>()
    val address: LiveData<List<Selection<Address>>>
        get() = _address

    private var _selectedAddress: Address? = null
    val selectedAddress: Address?
        get() = _selectedAddress

    companion object {
        val addressDummy = listOf(
            Address(
                id = 1,
                label = "Rumah",
                name = "Noge Bams",
                phone = "087123456789",
                address = "Jl Letjen Suprapto No 18, Parit Benut, Kabupaten Karimun, Kepri, Indonesia",
                note = "Cat warna kuning"
            ),
            Address(
                id = 2,
                label = "Kantor",
                name = "Noge Bams",
                phone = "087123456789",
                address = "Jl Soekarno No 4, Parit Benut, Kabupaten Karimun, Kepri, Indonesia",
                note = "Gedung Merah Putih"
            )
        )
    }

    init {
        _address.value = addressDummy.map {
            Selection(
                isSelected = false,
                data = it
            )
        }
    }

    fun selectAddress(id: Int) {
        _address.value?.let { items ->
            _address.value = items.map {
                if (it.data.id == id) {
                    _selectedAddress = it.data
                }
                Selection(
                    isSelected = it.data.id == id,
                    data = it.data
                )
            }
        }
    }
}