package com.example.interviewtask.ui

import androidx.lifecycle.*
import androidx.paging.*
import com.example.interviewtask.data.MyApi
import kotlinx.coroutines.flow.cache


class StockersViewModel(
        private val api: MyApi,
) : ViewModel() {
    var stockers = Pager(PagingConfig(pageSize = 10)) {
        DataRepository(api)
    }.flow.cachedIn(viewModelScope)
}