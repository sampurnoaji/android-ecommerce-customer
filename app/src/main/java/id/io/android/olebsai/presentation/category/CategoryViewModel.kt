package id.io.android.olebsai.presentation.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.data.source.remote.product.ProductPagingSource.Companion.ITEMS_PER_PAGE
import id.io.android.olebsai.domain.model.category.Category
import id.io.android.olebsai.domain.model.category.CategoryType
import id.io.android.olebsai.domain.model.product.WProduct
import id.io.android.olebsai.domain.repository.ProductRepository
import id.io.android.olebsai.util.ui.Selection
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: ProductRepository,
) : ViewModel() {

    private val initialCategories = listOf(
        Category(id = 1, CategoryType.ALL),
        Category(id = 2, CategoryType.CRAFT),
        Category(id = 3, CategoryType.FASHION),
        Category(id = 4, CategoryType.CULINARY),
    ).map {
        Selection(
            isSelected = false,
            data = it
        )
    }

    private val _categories = MutableLiveData(initialCategories)
    val categories: LiveData<List<Selection<Category>>>
        get() = _categories

    val products: Flow<PagingData<WProduct>> = Pager(
        config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { repository.getProductPagingSource("") }
    ).flow.cachedIn(viewModelScope)

    init {
        onCategoryChanged(initialCategories[0].data.id)
    }

    fun getSearchProductStream(query: String): Flow<PagingData<WProduct>> {
        return repository.getProductList(query)
    }

    fun onCategoryChanged(id: Int) {
        _categories.value = initialCategories.map {
            it.copy(isSelected = it.data.id == id)
        }
    }
}