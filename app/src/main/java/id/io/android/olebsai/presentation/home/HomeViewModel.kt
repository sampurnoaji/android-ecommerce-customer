package id.io.android.olebsai.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.R
import id.io.android.olebsai.domain.model.category.Category
import id.io.android.olebsai.domain.model.category.CategoryType
import id.io.android.olebsai.domain.model.product.Product
import id.io.android.olebsai.domain.model.voucher.Voucher
import id.io.android.olebsai.domain.usecase.product.GetProductPagingSourceUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductPagingSourceUseCase: GetProductPagingSourceUseCase
) : ViewModel() {

    companion object {
        private const val ITEMS_PER_PAGE = 10
    }

    val images = listOf(
        R.drawable.banner1,
        R.drawable.banner2,
        R.drawable.banner3,
        R.drawable.banner4,
        R.drawable.banner5,
    )

    val categories = listOf(
        Category(id = 1, CategoryType.ALL),
        Category(id = 2, CategoryType.CRAFT),
        Category(id = 3, CategoryType.FASHION),
        Category(id = 4, CategoryType.CULINARY),
    )

    val vouchers = listOf(
        Voucher(id = 1, name = "Cashback Rp 10.000", desc = "Sisa 5 hari lagi"),
        Voucher(id = 1, name = "Gratis Ongkir", desc = "Sisa 2 hari lagi"),
    )

    val products: Flow<PagingData<Product>> = Pager(
        config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { getProductPagingSourceUseCase() }
    ).flow.cachedIn(viewModelScope)
}