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
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var newsAdapter: NewsAdapter
    private val viewModel: NewsViewModel by viewModels()
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
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_favoritesFragment_to_detailFragment, bundle)
        }

        viewModel.getFavoriteNews().observe(viewLifecycleOwner){news->
            newsAdapter.differ.submitList(news)

        }
        newsAdapter.setOnFavoriteButtonClickListener {news->
            viewModel.deleteFavoriteNews(news)
            Snackbar.make(view,"News deleted successfully", Snackbar.LENGTH_SHORT).apply {
                setAction("Undo"){
                    viewModel.favorite(news)
                }
            } .show()
        }
    }

    private fun setupRecyclerView(){
        newsAdapter = NewsAdapter()
        binding.favoriteRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}