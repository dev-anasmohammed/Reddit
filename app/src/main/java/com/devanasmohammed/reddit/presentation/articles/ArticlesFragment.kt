package com.devanasmohammed.reddit.presentation.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.devanasmohammed.reddit.R
import com.devanasmohammed.reddit.data.model.Result
import com.devanasmohammed.reddit.data.remote.repositories.ArticlesRepo
import com.devanasmohammed.reddit.databinding.FragmentArticlesBinding

class ArticlesFragment : Fragment() {

    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ArticlesViewModel
    private lateinit var adapter: ArticlesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_articles, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerView()
        viewModel.article.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Success -> {
                    adapter.submitList(result.data?.toList()!!)
                }
                is Result.Loading -> {}
                is Result.Error -> {}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViewModel() {
        val factory = ArticlesViewModelFactory(ArticlesRepo())
        viewModel = ViewModelProvider(this, factory)[ArticlesViewModel::class.java]
    }

    private fun setupRecyclerView() {
        adapter = ArticlesAdapter(requireActivity())
        binding.articlesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.articlesRv.adapter = adapter
        binding.articlesRv.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                LinearLayoutManager.VERTICAL
            )
        )
    }

}