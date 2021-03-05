package com.example.interviewtask.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.interviewtask.data.MyApi
import com.example.interviewtask.databinding.StockersFragmentBinding
import com.example.interviewtask.ui.StockAdapter
import com.example.interviewtask.ui.StockersLoadStateAdapter
import com.example.interviewtask.ui.StockersViewModel
import com.example.interviewtask.ui.StockersViewModelFactory
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest


class StockFragment : Fragment()  {

    private lateinit var viewModel: StockersViewModel
    private lateinit var binding: StockersFragmentBinding
    val stockersAdapter = StockAdapter()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ) = StockersFragmentBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val factory = StockersViewModelFactory(MyApi())
        viewModel = ViewModelProvider(this, factory).get(StockersViewModel::class.java)

        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = stockersAdapter.withLoadStateHeaderAndFooter(
                    header = StockersLoadStateAdapter { stockersAdapter.retry() },
                    footer = StockersLoadStateAdapter { stockersAdapter.retry() })
        }

        stockersAdapter.notifyDataSetChanged()

        lifecycleScope.launch {
            viewModel.stockers.collectLatest { pagedData ->
                stockersAdapter.submitData(pagedData)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        binding==null
    }
}



