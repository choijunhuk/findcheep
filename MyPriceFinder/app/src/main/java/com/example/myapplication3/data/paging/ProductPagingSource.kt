package com.example.myapplication3.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication3.data.api.NaverSearchApi
import com.example.myapplication3.data.model.NaverShopItem
import com.example.myapplication3.util.Constants.PAGING_STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductPagingSource @Inject constructor(
    private val api: NaverSearchApi,
    private val clientId: String,
    private val clientSecret: String,
    private val query: String
) : PagingSource<Int, NaverShopItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NaverShopItem> {
        val page = params.key ?: PAGING_STARTING_PAGE_INDEX
        return try {
            val response = api.searchShopping(
                clientId = clientId,
                clientSecret = clientSecret,
                query = query,
                start = page,
                display = params.loadSize
            )
            val items = response.items
            LoadResult.Page(
                data = items,
                prevKey = if (page == PAGING_STARTING_PAGE_INDEX) null else page - params.loadSize,
                nextKey = if (items.isEmpty()) null else page + params.loadSize
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NaverShopItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(state.config.pageSize)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(state.config.pageSize)
        }
    }
}