package com.skl.newsapp.ui.Search

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.skl.newsapp.R
import com.skl.newsapp.data.repository.local.model.DomainArticle
import com.skl.newsapp.databinding.FragmentSearchNewsBinding
import com.skl.newsapp.ui.shared.NewsAdapter
import com.skl.newsapp.utils.Constants.EDIT_TEXT_DELAY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchNewsFragment : Fragment(), NewsAdapter.OnItemClickListener {

    private var _binding: FragmentSearchNewsBinding? = null
    private val binding get() = _binding!!

    private val viewmodel: SearchViewModel by viewModels()

    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentSearchNewsBinding.inflate(layoutInflater, container, false)

        newsAdapter = NewsAdapter(this)
        binding.apply {
            searchRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            searchRecyclerview.adapter = newsAdapter
            searchRecyclerview.setHasFixedSize(true)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel.searchedNewsArticles.observe(viewLifecycleOwner, {
            Log.i("SearchFrag", "onViewCreated: the data is: ${it.data}")
            if (it != null) {
                newsAdapter.submitList(it.data)
                Log.i("SearchFrag", "onViewCreated: data successfully loaded ${it.data}")
            } else {
                Log.i("SearchFrag", "onViewCreated: data failed to loaded ${it}")
            }
        })

        setHasOptionsMenu(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.toolbar_menu, menu)

        val searchItem = menu.findItem(R.id.search_view)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank()) {
                    MainScope().launch {
                        delay(EDIT_TEXT_DELAY)
                        newText.let { viewmodel.searchQuery.value = it }
                    }
                }
                return false
            }
        })
    }

    override fun OnItemClick(domainArticle: DomainArticle) {
        val action = SearchNewsFragmentDirections.actionSearchNewsFragmentToDetailedFragment(domainArticle)
        findNavController().navigate(action)
    }
}