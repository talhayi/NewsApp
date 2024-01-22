package com.example.newsappexample.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappexample.R
import com.example.newsappexample.databinding.FragmentSearchBinding
import com.example.newsappexample.ui.adapter.NewsPagingAdapter
import com.example.newsappexample.ui.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var newsPagingAdapter: NewsPagingAdapter
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
        searchNewsObserve()
        onItemClick()
        onFavoriteClick(view)
    }

    private fun onItemClick(){
        newsPagingAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable(getString(R.string.article), it)
            }
            findNavController().navigate(R.id.action_searchFragment_to_detailFragment, bundle)
        }
    }

    private fun onFavoriteClick(view: View){
        newsPagingAdapter.setOnFavoriteButtonClickListener {
            viewModel.favorite(it)
            Snackbar.make(view, getString(R.string.news_saved_successfully), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun searchNewsObserve() {
        binding.apply {
            with(viewModel) {
                searchNews.observe(viewLifecycleOwner) { searchNews ->
                    lifecycleScope.launch {
                        newsPagingAdapter.submitData(searchNews)
                    }
                    newsPagingAdapter.addLoadStateListener { loadState ->
                        if (loadState.refresh is LoadState.Loading ||
                            loadState.append is LoadState.Loading
                        ) {
                            paginationProgressBar.visibility = View.VISIBLE
                        } else {
                            paginationProgressBar.visibility = View.GONE
                        }

                    }
                }
            }
        }
    }
    private fun searchView() {
        var job: Job? = null
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
                    job?.cancel()
                    job = MainScope().launch {
                        delay(1000L)
                        if (it.isNotEmpty()) {
                            viewModel.getSearchNews(it)
                        }
                    }
                }
                return true
            }
        })
    }
    private fun setupRecyclerView(){
        newsPagingAdapter = NewsPagingAdapter()
        binding.searchRecyclerView.apply {
            adapter = newsPagingAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}