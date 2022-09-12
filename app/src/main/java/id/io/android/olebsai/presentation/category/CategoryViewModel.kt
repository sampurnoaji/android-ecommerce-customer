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
import id.io.android.olebsai.domain.model.category.Category
import id.io.android.olebsai.domain.model.category.CategoryType
import id.io.android.olebsai.domain.model.product.Product
import id.io.android.olebsai.domain.usecase.product.GetProductPagingSourceUseCase
import id.io.android.olebsai.util.ui.Selection
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getProductPagingSourceUseCase: GetProductPagingSourceUseCase
) : ViewModel() {

    companion object {
        private const val ITEMS_PER_PAGE = 10
    }

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

    val products: Flow<PagingData<Product>> = Pager(
        config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { getProductPagingSourceUseCase() }
    ).flow.cachedIn(viewModelScope)

    init {
        onCategoryChanged(initialCategories[0].data.id)
    }

    fun onCategoryChanged(id: Int) {
        _categories.value = initialCategories.map {
            it.copy(isSelected = it.data.id == id)
        }
    }
}