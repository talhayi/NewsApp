package com.example.newsappexample.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsappexample.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackClick()
        webView()
    }

    private fun onBackClick(){
        binding.backImageView.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun webView(){
        val args: DetailFragmentArgs by navArgs()
        val news = args.article
        binding.detailWebView.apply {
            webViewClient = WebViewClient()
            news.url?.let { loadUrl(it) }
        }
    }
}