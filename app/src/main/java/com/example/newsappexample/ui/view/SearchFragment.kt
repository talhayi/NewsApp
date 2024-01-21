package com.example.newsappexample.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappexample.databinding.FragmentSearchBinding
import com.example.newsappexample.ui.adapter.NewsAdapter
import com.example.newsappexample.ui.viewmodel.NewsViewModel
import com.example.newsappexample.util.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        searchView()
        viewModel.searchNews.observe(viewLifecycleOwner){newsResponse->
            when(newsResponse){
                is Result.Success->{
                    binding.paginationProgressBar.visibility = View.INVISIBLE //TODO extansions'a çevir
                    newsResponse.data.let {
                        newsAdapter.differ.submitList(newsResponse.data.articles)
                    }
                }
                is Result.Error->{
                    newsResponse.message.let { message->
                        Toast.makeText(requireContext(),"An error occurred $message", Toast.LENGTH_SHORT).show()
                    }
                }
                is Result.Loading-> {
                    binding.paginationProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }
    private fun searchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let {
                    if (it.isNotEmpty()) {
                        viewModel.getSearchNews(it)
                    }
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                p0?.let {
                    if (it.isNotEmpty()) {
                        viewModel.getSearchNews(it)
                    }
                }
                return true
            }
        })
    }
    private fun setupRecyclerView(){
        newsAdapter = NewsAdapter()
        binding.searchRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}