package com.example.interviewtask.ui

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.interviewtask.data.MyApi
import com.example.interviewtask.databinding.StockersFragmentBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import java.util.*

class StockFragment : Fragment() {

    private lateinit var viewModel: StockersViewModel
    private lateinit var binding: StockersFragmentBinding
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

        val stockersAdapter = StockAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = stockersAdapter.withLoadStateHeaderAndFooter(
                header = StockersLoadStateAdapter { stockersAdapter.retry() },
                footer = StockersLoadStateAdapter { stockersAdapter.retry() }
        )
        stockersAdapter.notifyDataSetChanged()
        lifecycleScope.launch {
            viewModel.stockers.collectLatest { pagedData ->
                if(pagedData!=null) {
                    stockersAdapter.submitData(pagedData)
                }
                else {
                    Toast.makeText(context, "Server problem!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
