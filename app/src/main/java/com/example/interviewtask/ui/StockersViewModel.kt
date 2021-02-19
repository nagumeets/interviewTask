package com.example.interviewtask.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.interviewtask.data.MyApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class StockersViewModel(
    private val api: MyApi,
) : ViewModel() {
    val stockers = Pager(PagingConfig(pageSize = 10)) {
        DataRepository(api)
    }.flow.cachedIn(viewModelScope)
    @ExperimentalCoroutinesApi
    private val searchChanel = ConflatedBroadcastChannel<String>()

//We will use a ConflatedBroadcastChannel as this will only broadcast
//the most recent sent element to all the subscribers
@ExperimentalCoroutinesApi
    fun setSearchQuery(search: String) {
//We use .offer() to send the element to all the subscribers.
        searchChanel.offer(search)
    }
}