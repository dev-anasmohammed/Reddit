package com.devanasmohammed.reddit.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.devanasmohammed.reddit.R
import com.devanasmohammed.reddit.databinding.FragmentSplashBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_splash, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            appNameAndSloganAnimations()
            delay(1500)
            findNavController().navigate(
                R.id.action_splashFragment_to_articlesFragment
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun leftToRightAnimationWithAlpha(view: View ){
        view.animate().alpha(1f).duration = 600
        view.startAnimation(
            AnimationUtils.loadAnimation(
                requireContext(),
                R.anim.slide_right,
            ),
        )
    }

    private suspend fun appNameAndSloganAnimations(){
        //redd animation
        leftToRightAnimationWithAlpha(binding.nameS1Tv)
        delay(500)
        //i animation
        leftToRightAnimationWithAlpha(binding.nameS2Tv)
        delay(300)
        //t animation
        leftToRightAnimationWithAlpha(binding.nameS3Tv)
        delay(500)
        //slogan animation
        binding.sloganTv.animate().alpha(1f).duration = 1000

    }

}