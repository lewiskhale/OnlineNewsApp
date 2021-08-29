package com.skl.newsapp.ui.DetailedView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.skl.newsapp.R
import com.skl.newsapp.databinding.FragmentDetailedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailedFragment : Fragment() {

    private val args:DetailedFragmentArgs by navArgs()

    private val viewmodel: DetailsViewModel by viewModels()

    private var _binding: FragmentDetailedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailedBinding.inflate(layoutInflater, container, false)

        val article = args.roomArticle

        binding.apply {
            if(article.saved){
                webviewSaveButton.setImageResource(R.drawable.ic_delete)
            }

            webview.apply {
                webViewClient = WebViewClient()
                loadUrl(article.url)
            }

            webviewSaveButton.setOnClickListener {
                article.saved = !article.saved
                viewmodel.saveArticle(article)
            }
        }
        viewmodel.isSaved.observe(viewLifecycleOwner, { saved ->
            if(!saved){
                binding.webviewSaveButton.setImageResource(R.drawable.ic_saved)
            }else{
                binding.webviewSaveButton.setImageResource(R.drawable.ic_delete)
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}