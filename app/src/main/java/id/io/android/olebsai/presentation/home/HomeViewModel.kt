package id.io.android.olebsai.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.R
import id.io.android.olebsai.domain.model.user.FrontPageData
import id.io.android.olebsai.domain.model.voucher.Voucher
import id.io.android.olebsai.domain.repository.UserRepository
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.SingleLiveEvent
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _frontPageData = SingleLiveEvent<LoadState<FrontPageData>>()
    val frontPageData: LiveData<LoadState<FrontPageData>>
        get() = _frontPageData

    val images = listOf(
        R.drawable.banner1,
        R.drawable.banner2,
        R.drawable.banner3,
        R.drawable.banner4,
        R.drawable.banner5,
    )

//    val categories = listOf(
//        Category(id = 1, CategoryType.ALL),
//        Category(id = 2, CategoryType.CRAFT),
//        Category(id = 3, CategoryType.FASHION),
//        Category(id = 4, CategoryType.CULINARY),
//    )

    val vouchers = listOf(
        Voucher(id = 1, name = "Cashback Rp 10.000", desc = "Sisa 5 hari lagi"),
        Voucher(id = 1, name = "Gratis Ongkir", desc = "Sisa 2 hari lagi"),
    )

    fun getFrontPageData() {
        _frontPageData.value = LoadState.Loading
        viewModelScope.launch {
            _frontPageData.value = repository.getFrontPageData()
        }
    }
}