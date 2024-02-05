package com.example.newsappexample.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappexample.R
import com.example.newsappexample.databinding.FragmentHomeBinding
import com.example.newsappexample.ui.adapter.NewsPagingAdapter
import com.example.newsappexample.ui.viewmodel.NewsViewModel
import com.example.newsappexample.util.hide
import com.example.newsappexample.util.observe
import com.example.newsappexample.util.show
import com.example.newsappexample.util.showSnackBar
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: NewsViewModel by viewModels()
    @Inject
    lateinit var newsPagingAdapter: NewsPagingAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        onClickChipButton()
        allNewsObserve()
        onItemClick()
        onFavoriteClick(view)
    }

    private fun onItemClick(){
        newsPagingAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable(getString(R.string.article), it)
            }
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
        }
    }

    private fun onFavoriteClick(view: View){
        newsPagingAdapter.setOnFavoriteButtonClickListener {news->
            val url = news.url
            viewModel.favorite(news, url!!)
            showSnackBar(view, getString(R.string.news_saved_successfully))
        }
    }

    private fun allNewsObserve() {
        binding.apply {
            with(viewModel) {
                observe(allNews) { allNews ->
                    lifecycleScope.launch {
                        newsPagingAdapter.submitData(newsPagingAdapter.emptyPagingData)
                        newsPagingAdapter.submitData(allNews)
                    }
                    newsPagingAdapter.addLoadStateListener { loadState ->
                        if (loadState.refresh is LoadState.Loading ||
                            loadState.append is LoadState.Loading
                        ) {
                            paginationProgressBar.show()
                        } else {
                            paginationProgressBar.hide()
                        }
                    }
                }
            }
        }
    }

    private fun onClickChipButton() {
        val chipGroup = binding.chipGroup
        var lastClickedChipIndex = 0

        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip

            chip.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    lastClickedChipIndex = i
                    if (i == 0) {
                        viewModel.getAllNews("")
                    } else {
                        viewModel.getAllNews(chip.text.toString())
                    }
                }
            }
            if (i == 0) {
                chip.isChecked = true
            }
        }
        viewModel.getAllNews("")
    }

    private fun setupRecyclerView(){
        binding.homeRecyclerView.apply {
            adapter = newsPagingAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}