package com.skl.newsapp.ui.Saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.skl.newsapp.R
import com.skl.newsapp.data.repository.local.model.DomainArticle
import com.skl.newsapp.databinding.FragmentDetailedBinding
import com.skl.newsapp.databinding.FragmentSavedNewsBinding
import com.skl.newsapp.ui.DetailedView.DetailsViewModel
import com.skl.newsapp.ui.shared.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedNewsFragment : Fragment(), NewsAdapter.OnItemClickListener {

    private var _binding: FragmentSavedNewsBinding? = null
    private val binding get() = _binding!!

    private val viewmodel: SavedViewModel by viewModels()

    private val adapter = NewsAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSavedNewsBinding.inflate(layoutInflater, container, false)

        binding.apply {
            searchRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            searchRecyclerview.setHasFixedSize(true)
            searchRecyclerview.adapter = adapter
        }

        viewmodel.savedArticles.observe(viewLifecycleOwner, { articles ->
            adapter.submitList(articles)
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun OnItemClick(domainArticle: DomainArticle) {
        val action  = SavedNewsFragmentDirections.actionSavedNewsFragmentToDetailedFragment(domainArticle)
        findNavController().navigate(action)
    }
}