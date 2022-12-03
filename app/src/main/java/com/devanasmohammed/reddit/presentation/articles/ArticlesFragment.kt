package com.devanasmohammed.reddit.presentation.articles

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.devanasmohammed.reddit.R
import com.devanasmohammed.reddit.data.local.LocalDatabase
import com.devanasmohammed.reddit.data.model.Result
import com.devanasmohammed.reddit.data.remote.repositories.ArticlesRepo
import com.devanasmohammed.reddit.databinding.FragmentArticlesBinding
import com.devanasmohammed.reddit.util.ProgressBarHandler
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class ArticlesFragment : Fragment() {

    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ArticlesViewModel
    private lateinit var adapter: ArticlesAdapter
    private lateinit var progressBar: ProgressBarHandler

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

        progressBar = ProgressBarHandler(requireActivity(), binding.root.id)
        setupViewModel()
        setupRecyclerView()

        viewModel.articles.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    progressBar.show()
                }
                is Result.Success -> {
                    progressBar.hide()
                    adapter.submitList(result.data?.toList()!!)
                }
                is Result.Error -> {
                    progressBar.hide()
                    MaterialAlertDialogBuilder(requireContext())
                        .setMessage(result.message.toString())
                        .setPositiveButton("Ok", null)
                        .setNegativeButton("Offline Mode") { _, _ ->
                            viewModel.getLocalArticles()
                        }
                        .show()
                }
            }
        }

        adapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_articlesFragment_to_articleFragment,
                bundle
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViewModel() {
        val factory =
            ArticlesViewModelFactory(ArticlesRepo(LocalDatabase.getDatabase(requireContext())))
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