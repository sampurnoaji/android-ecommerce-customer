package id.io.android.olebsai.presentation.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.data.source.remote.product.ProductPagingSource.Companion.ITEMS_PER_PAGE
import id.io.android.olebsai.domain.model.product.Category
import id.io.android.olebsai.domain.model.product.WProduct
import id.io.android.olebsai.domain.repository.ProductRepository
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.SingleLiveEvent
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: ProductRepository,
) : ViewModel() {

    private var query: String = ""
    private var kategoriId: String? = null

    private val _categoriesResult = SingleLiveEvent<LoadState<List<Category>>>()
    val categoriesResult: LiveData<LoadState<List<Category>>>
        get() = _categoriesResult

    val products: Flow<PagingData<WProduct>> = Pager(
        config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { repository.getProductPagingSource(query, kategoriId) }
    ).flow.cachedIn(viewModelScope)

    fun getCategories() {
        _categoriesResult.value = LoadState.Loading
        viewModelScope.launch {
            _categoriesResult.value = repository.getCategories()
        }
    }

    fun onQueryChanged(query: String) {
        this.query = query
    }

    fun onCategoryChanged(kategoriId: String?) {
        this.kategoriId = kategoriId
//        getSearchProductStream()
    }
}