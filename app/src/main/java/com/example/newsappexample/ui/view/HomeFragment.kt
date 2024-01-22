package com.example.newsappexample.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappexample.R
import com.example.newsappexample.databinding.FragmentHomeBinding
import com.example.newsappexample.ui.adapter.NewsAdapter
import com.example.newsappexample.ui.viewmodel.NewsViewModel
import com.example.newsappexample.util.Result
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClickChipButton()
        setupRecyclerView()
        allNews()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
        }
        newsAdapter.setOnFavoriteButtonClickListener {
            viewModel.favorite(it)
            Snackbar.make(view,"News saved successfully", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun allNews(){
        viewModel.allNews.observe(viewLifecycleOwner){newsResponse->
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
        newsAdapter = NewsAdapter()
        binding.homeRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }
}