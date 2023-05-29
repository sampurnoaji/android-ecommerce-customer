package id.io.android.olebsai.presentation.basket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.domain.model.basket.BasketItem
import id.io.android.olebsai.domain.repository.BasketRepository
import id.io.android.olebsai.util.LoadState
import id.io.android.olebsai.util.SingleLiveEvent
import id.io.android.olebsai.util.ui.Selection
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val repository: BasketRepository
) : ViewModel() {

    private val _basketItems = SingleLiveEvent<LoadState<List<BasketItem>>>()
    val basketItems: LiveData<LoadState<List<BasketItem>>>
        get() = _basketItems

    private val _items = MutableLiveData<List<ProductBasketListAdapter.Item>>()
    val items: LiveData<List<ProductBasketListAdapter.Item>>
        get() = _items

    private val _updateQtyResult = SingleLiveEvent<LoadState<String>>()
    val updateQtyResult: LiveData<LoadState<String>>
        get() = _updateQtyResult

    private val _updateNoteResult = SingleLiveEvent<LoadState<String>>()
    val updateNoteResult: LiveData<LoadState<String>>
        get() = _updateNoteResult

    private val _removeProductResult = SingleLiveEvent<LoadState<String>>()
    val removeProductResult: LiveData<LoadState<String>>
        get() = _removeProductResult

    private val _checkoutResult = SingleLiveEvent<LoadState<String>>()
    val checkoutResult: LiveData<LoadState<String>>
        get() = _checkoutResult

    val selectedProducts: LiveData<List<Pair<Int, BasketItem>>> = Transformations.map(_items) {
        it.filter { item ->
            item.viewType == ProductBasketListAdapter.CONTENT_TYPE
        }.filter { item ->
            item.content!!.second.isSelected
        }.map { item ->
            Pair(item.content!!.first, item.content.second.data)
        }
    }

    fun getBasket() {
        _basketItems.value = LoadState.Loading
        viewModelScope.launch {
            _basketItems.value = repository.getBasket()
        }
    }

    fun setBasketProducts(products: List<BasketItem>) {
        val items = mutableListOf<ProductBasketListAdapter.Item>()
        val group = products.groupBy { it.product.tokoId }
        group.forEach {
            items.add(
                ProductBasketListAdapter.Item(
                    viewType = ProductBasketListAdapter.HEADER_TYPE,
                    header = it.key
                )
            )
            val groupedProducts = it.value.groupBy { basket -> basket.product.produkId }
            groupedProducts.forEach { groupedProduct ->
                val count = groupedProduct.value.count { basket ->
                    basket.product.produkId == groupedProduct.key
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

    fun selectProduct(productId: String) {
        _items.value = _items.value!!.map {
            if (it.viewType == ProductBasketListAdapter.CONTENT_TYPE)
                it.copy(
                    content = it.content?.copy(
                        second = it.content.second.copy(
                            isSelected =
                            if (it.content.second.data.product.produkId == productId) !it.content.second.isSelected
                            else it.content.second.isSelected
                        )
                    )
                )
            else it
        }
    }

    fun updateQty(basketId: String, qty: Int) {
        _updateQtyResult.value = LoadState.Loading
        viewModelScope.launch {
            _updateQtyResult.value = repository.updateQty(basketId, qty)
        }
    }

    fun updateNote(basketId: String, note: String) {
        _updateNoteResult.value = LoadState.Loading
        viewModelScope.launch {
            _updateNoteResult.value = repository.updateNote(basketId, note)
        }
    }

    fun removeProduct(productId: String) {
        _removeProductResult.value = LoadState.Loading
        viewModelScope.launch {
            _removeProductResult.value = repository.removeProduct(productId)
        }
    }

    fun checkout(basketIds: List<String>, namaJasaPengiriman: String) {
        _checkoutResult.value = LoadState.Loading
        viewModelScope.launch {
            _checkoutResult.value = repository.checkout(basketIds, namaJasaPengiriman)
        }
    }
}