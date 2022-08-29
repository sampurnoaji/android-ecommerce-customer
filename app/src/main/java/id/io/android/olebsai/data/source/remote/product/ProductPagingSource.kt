package id.io.android.olebsai.data.source.remote.product

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.io.android.olebsai.domain.model.product.Product
import javax.inject.Inject

class ProductPagingSource @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource
) : PagingSource<Int, Product>() {

    companion object {
        private const val STARTING_KEY = 0
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val position = params.key ?: STARTING_KEY

        return try {
            val response = remoteDataSource.getProductList(position)
            val products = response!!.products!!
            LoadResult.Page(
                data = products.map { it.toDomain() },
                prevKey = if (position == STARTING_KEY) null else position - 1,
                nextKey = if (position == 10) null else position + (params.loadSize / 10)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}