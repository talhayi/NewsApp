package com.example.newsappexample.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappexample.R
import com.example.newsappexample.databinding.FragmentFavoritesBinding
import com.example.newsappexample.ui.adapter.NewsAdapter
import com.example.newsappexample.ui.viewmodel.NewsViewModel
import com.example.newsappexample.util.showSnackbarWithAction
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel: NewsViewModel by viewModels()
    @Inject
    lateinit var newsAdapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        onItemClick()
        onItemDeleteClick(view)
        favoriteNewsObserve()
    }

    private fun favoriteNewsObserve(){
        viewModel.getFavoriteNews().observe(viewLifecycleOwner){news->
            newsAdapter.differ.submitList(news)
        }
    }

    private fun onItemClick(){
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable(getString(R.string.article), it)
            }
            findNavController().navigate(R.id.action_favoritesFragment_to_detailFragment, bundle)
        }
    }

    private fun onItemDeleteClick(view: View){
        newsAdapter.setOnFavoriteButtonClickListener {news->
            viewModel.deleteFavoriteNews(news)
            val url = news.url
            showSnackbarWithAction(view, getString(R.string.news_deleted_successfully),getString(R.string.undo)){
                viewModel.favorite(news, url!!)
            }
        }
    }

    private fun setupRecyclerView(){
        binding.favoriteRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}