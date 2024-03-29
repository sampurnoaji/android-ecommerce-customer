package id.io.android.olebsai.presentation.order.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.io.android.olebsai.data.source.remote.basket.OrderPagingSource
import id.io.android.olebsai.data.source.remote.product.ProductPagingSource
import id.io.android.olebsai.domain.model.order.Order
import id.io.android.olebsai.domain.model.order.OrderDetail
import id.io.android.olebsai.domain.repository.BasketRepository
import id.io.android.olebsai.util.LoadState
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val basketRepository: BasketRepository
): ViewModel() {

    private val _activeOrdersResult = MutableLiveData<LoadState<List<Order>>>()
    val activeOrdersResult: LiveData<LoadState<List<Order>>>
        get() = _activeOrdersResult

    val doneOrders: Flow<PagingData<Order>> = Pager(
        config = PagingConfig(pageSize = OrderPagingSource.ITEMS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { basketRepository.getOrderPagingSource() }
    ).flow.cachedIn(viewModelScope)

    private val _orderDetailResult = MutableLiveData<LoadState<OrderDetail>>()
    val orderDetailResult: LiveData<LoadState<OrderDetail>>
        get() = _orderDetailResult

    private val _payOrderResult = MutableLiveData<LoadState<Any>>()
    val payOrderResult: LiveData<LoadState<Any>>
        get() = _payOrderResult

    private val _finishOrderResult = MutableLiveData<LoadState<Any>>()
    val finishOrderResult: LiveData<LoadState<Any>>
        get() = _finishOrderResult

    fun getActiveOrders() {
        _activeOrdersResult.value = LoadState.Loading
        viewModelScope.launch {
            _activeOrdersResult.value = basketRepository.getActiveOrders()
        }
    }

    fun getOrderDetail(headerId: String) {
        _orderDetailResult.value = LoadState.Loading
        viewModelScope.launch {
            _orderDetailResult.value = basketRepository.getOrderDetail(headerId)
        }
    }

    fun payOrder(pesananHeaderId: String) {
        _payOrderResult.value = LoadState.Loading
        viewModelScope.launch {
            _payOrderResult.value = basketRepository.payOrder(pesananHeaderId)
        }
    }

    fun finishOrder(pesananHeaderId: String) {
        _finishOrderResult.value = LoadState.Loading
        viewModelScope.launch {
            _finishOrderResult.value = basketRepository.finishOrder(pesananHeaderId)
        }
    }
}