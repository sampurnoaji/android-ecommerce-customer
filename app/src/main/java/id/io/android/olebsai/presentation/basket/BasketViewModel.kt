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


    val selectedProducts: LiveData<List<Pair<Int, Product>>> = Transformations.map(_items) {
        it.filter { item ->
            item.viewType == ProductBasketListAdapter.CONTENT_TYPE
        }.filter { item ->
            item.content!!.second.isSelected
        }.map { item ->
            Pair(item.content!!.first, item.content.second.data)
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
            val groupedProducts = it.value.groupBy { product -> product.id }
            groupedProducts.forEach { groupedProduct ->
                val count = groupedProduct.value.count { product ->
                    product.id == groupedProduct.key
                }
                items.add(
                    ProductBasketListAdapter.Item(
                        viewType = ProductBasketListAdapter.CONTENT_TYPE,
                        content = Pair(
                            count, Selection(
                                isSelected = true,
                                data = groupedProduct.value[0]
                            )
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
                    content = it.content?.copy(
                        second = it.content.second.copy(
                            isSelected =
                            if (it.content.second.data.id == productId) !it.content.second.isSelected
                            else it.content.second.isSelected
                        )
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