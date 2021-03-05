package com.example.interviewtask.ui

import android.widget.Toast
import androidx.core.view.isGone
import androidx.paging.PagingSource
import com.example.interviewtask.data.MyApi
import com.example.interviewtask.data.models.StockOwner
import com.example.interviewtask.data.models.StockOwnerItem
import com.example.interviewtask.databinding.StockersFragmentBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.concurrent.Flow

private const val STARTING_PAGE_INDEX = 1

class DataRepository(
        private val api: MyApi,
) : PagingSource<Int, StockOwnerItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StockOwnerItem> {
        return try {
            val nextPageNumber = params.key ?: STARTING_PAGE_INDEX
            val response = api.getStockersData(nextPageNumber)
            LoadResult.Page(
                    data = response.items,
                    prevKey = if (nextPageNumber > STARTING_PAGE_INDEX) nextPageNumber - 1 else null,
                    nextKey = if (nextPageNumber < response.quota_max) nextPageNumber + 1 else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}