package com.skl.newsapp.ui.BreakingNews

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.skl.newsapp.R
import com.skl.newsapp.data.repository.local.model.DomainArticle
import com.skl.newsapp.databinding.FragmentBreakingNewsBinding
import com.skl.newsapp.ui.shared.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(), NewsAdapter.OnItemClickListener {

    private var _binding: FragmentBreakingNewsBinding? = null
    private val binding get() = _binding!!

    private var countrySelected = "za"

    private val viewmodel: BreakingNewsViewModel by viewModels()

    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentBreakingNewsBinding.inflate(layoutInflater, container, false)

        newsAdapter = NewsAdapter(this)
        binding.apply {
            breakingNewsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            breakingNewsRecyclerview.adapter = newsAdapter
            breakingNewsRecyclerview.setHasFixedSize(true)
        }
        setUpChips()
        setHasOptionsMenu(true)
        return binding.root
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.breakingNewsArticles.observe(viewLifecycleOwner, Observer {
            if (it.data != null) {
                newsAdapter.submitList(it.data)
//                Log.i("TAG", "onViewCreated: data successfully loaded ${it.data}")
            }
        })

        binding.apply {
            swipeRefresh.setOnRefreshListener {
                swipeRefresh.isRefreshing = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun OnItemClick(domainArticle: DomainArticle) {
        val action = BreakingNewsFragmentDirections.actionBreakingNewsFragmentToDetailedFragment(
            domainArticle
        )
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.countries_toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.za->{
                item.isChecked = true
                countrySelected = "za"
                viewmodel.countryCode.value = countrySelected
                return true
            }
            R.id.us->{
                item.isChecked = true
                countrySelected = "us"
                viewmodel.countryCode.value = countrySelected
                return true
            }
            R.id.gb->{
                item.isChecked = true
                countrySelected = "gb"
                viewmodel.countryCode.value = countrySelected
                return true
            }
            R.id.ca->{
                item.isChecked = true
                countrySelected = "ca"
                viewmodel.countryCode.value = countrySelected
                return true
            }
            R.id.au->{
                item.isChecked = true
                countrySelected = "au"
                viewmodel.countryCode.value = countrySelected
                return true
            }
        }
        return false
    }

    private fun setUpChips() {
        val categories = resources.getStringArray(R.array.categories)
        binding.apply {
            chipGroup.isSingleSelection = true
            for (category in categories) {
                val chip = Chip(chipGroup.context)
                chip.text = category.toString()
                chip.isClickable = true
                chip.chipStrokeWidth = 2f
                chip.isCheckable = true
                chip.isSingleLine = true
                chipGroup.addView(chip)
            }
            chipGroup.setOnCheckedChangeListener { group, checkedId ->
                val chipName: Chip? = group.findViewById(checkedId)
                //TODO update the category val in viewmodel
                if (chipName != null) Log.i("TAG", "setUpChips: ${chipName.text}")
            }
        }
    }

}