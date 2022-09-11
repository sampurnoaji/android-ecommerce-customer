package id.io.android.olebsai.presentation.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.model.product.Product
import id.io.android.olebsai.domain.usecase.product.ProductUseCases
import id.io.android.olebsai.util.ui.Selection
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val productUseCases: ProductUseCases
) : ViewModel() {

    private val _items = MutableLiveData<List<ProductBasketListAdapter.Item>>()
    val items: LiveData<List<ProductBasketListAdapter.Item>>
        get() = _items


    val selectedProducts: LiveData<List<Product>> = Transformations.map(_items) {
        it.filter { item ->
            item.viewType == ProductBasketListAdapter.CONTENT_TYPE
        }.filter { item ->
            item.content!!.isSelected
        }.map { item ->
            item.content!!.data
        }
    }

    fun setBasketProducts(products: List<Product>) {
        val items = mutableListOf<ProductBasketListAdapter.Item>()
        val group = products.groupBy { it.shopName }
        group.forEach {
            items.add(
                ProductBasketListAdapter.Item(
                    viewType = ProductBasketListAdapter.HEADER_TYPE,
                    header = it.key
                )
            )
            it.value.forEach { product ->
                items.add(
                    ProductBasketListAdapter.Item(
                        viewType = ProductBasketListAdapter.CONTENT_TYPE,
                        content = Selection(
                            isSelected = true,
                            data = product
                        )
                    )
                )
            }
        }
        _items.value = items
    }

    fun selectProduct(productId: Int) {
        _items.value = _items.value!!.map {
            if (it.viewType == ProductBasketListAdapter.CONTENT_TYPE)
                it.copy(
                    content = Selection(
                        isSelected =
                        if (it.content?.data?.id == productId) !it.content.isSelected
                        else it.content!!.isSelected,
                        data = it.content.data
                    )
                )
            else it
        }
    }

    fun deleteProduct(productId: Int) {
        viewModelScope.launch {
            productUseCases.deleteBasketProductUseCase(productId)
        }
    }
}