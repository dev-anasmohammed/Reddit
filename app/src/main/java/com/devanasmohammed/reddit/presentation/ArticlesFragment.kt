package com.devanasmohammed.reddit.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.devanasmohammed.reddit.R
import com.devanasmohammed.reddit.databinding.FragmentArticlesBinding
import com.devanasmohammed.reddit.databinding.FragmentSplashBinding


class ArticlesFragment : Fragment() {

    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_articles, container, false
        )
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}