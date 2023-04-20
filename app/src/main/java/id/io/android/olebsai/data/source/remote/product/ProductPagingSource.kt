package id.io.android.olebsai.data.source.remote.product

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.io.android.olebsai.domain.model.product.WProduct
import javax.inject.Inject

class ProductPagingSource @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource,
    private val query: String,
) : PagingSource<Int, WProduct>() {

    companion object {
        private const val STARTING_KEY = 1
        const val ITEMS_PER_PAGE = 10
    }

    override fun getRefreshKey(state: PagingState<Int, WProduct>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, WProduct> {
        val position = params.key ?: STARTING_KEY

        return try {
            val response = remoteDataSource.getProductList(
                page = position,
                size = params.loadSize,
                namaProduk = query,
            )
            val products = response.products.orEmpty().filter { it.status == "AKTIF" }
            val endOfPaginationReached = products.isEmpty()
            LoadResult.Page(
                data = products.map { it.toDomain() },
                prevKey = if (position == STARTING_KEY) null else position - 1,
                nextKey = if (endOfPaginationReached) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}