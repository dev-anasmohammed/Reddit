package com.devanasmohammed.reddit.presentation

import android.graphics.Paint
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.devanasmohammed.reddit.R
import com.devanasmohammed.reddit.data.model.Article
import com.devanasmohammed.reddit.databinding.FragmentArticleBinding
import com.devanasmohammed.reddit.util.ProgressBarHandler
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import io.noties.markwon.Markwon

class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private lateinit var progressBar: ProgressBarHandler
    private val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_article, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = ProgressBarHandler(requireActivity(),binding.root.id)
        progressBar.show()

        handleArticle(args.article)

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        lifecycle.addObserver(binding.youtubeVideoPlayer)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleArticle(article: Article) {
        binding.title.text = article.title
        binding.articleTitleTv.text = article.title
        binding.authorTv.text = article.author
        binding.publishedSinceTv.text = article.publishedSince

        //set underline & click
        binding.sourceUrlTv.text = article.sourceUrl
        binding.sourceUrlTv.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.sourceUrlTv.movementMethod = LinkMovementMethod.getInstance()

        val markdown = Markwon.create(requireContext())
        markdown.setMarkdown(binding.contentTv,article.content.toString())

        //check if not content just youtube video
        if (article.content == "") {
            if (article.sourceUrl!!.contains("youtu")) {
                setupVideoView(article.sourceUrl)
            }else{
                progressBar.hide()
            }
        }else{
            progressBar.hide()
        }
    }

    private fun setupVideoView(url: String) {
        progressBar.hide()

        binding.sourceTv.text = resources.getString(R.string.video_source)
        //hide content textView
        binding.contentTv.visibility = View.GONE
        //show videoPlayerView
        binding.youtubeVideoPlayer.visibility = View.VISIBLE
        binding.youtubeVideoPlayer.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = getYoutubeVideoId(url)
                Log.e("/*/*/","onReady")
                youTubePlayer.cueVideo(videoId, 0f)
            }
        })
    }

    private fun getYoutubeVideoId(url: String): String {
        //example https://youtu.be/72lYbTVlbmI
        if (url.contains("youtu.be")) {
            return url.substringAfter("be/")
            //example https://www.youtube.com/watch?v=72lYbTVlbmI
        } else if (url.contains("www.youtube.com")) {
            return url.substringAfter("v=")
        }
        return ""
    }

}