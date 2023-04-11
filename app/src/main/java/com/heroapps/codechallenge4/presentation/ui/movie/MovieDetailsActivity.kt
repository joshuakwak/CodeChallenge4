package com.heroapps.codechallenge4.presentation.ui.movie

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.heroapps.codechallenge4.R
import com.heroapps.codechallenge4.common.base.BaseActivity
import com.heroapps.codechallenge4.common.util.DateUtil.getConvertedDate
import com.heroapps.codechallenge4.common.util.collectLatestData
import com.heroapps.codechallenge4.common.util.createIntent
import com.heroapps.codechallenge4.common.util.getDrawableFromString
import com.heroapps.codechallenge4.data.source.remote.response.MovieItemResponse
import com.heroapps.codechallenge4.databinding.ActivityMovieBinding
import com.heroapps.codechallenge4.presentation.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsActivity : BaseActivity<MovieDetailsViewModel, ActivityMovieBinding>() {
    override val layoutId = R.layout.activity_movie
    override val viewModel: MovieDetailsViewModel by viewModels()

    private lateinit var progressDialog: ProgressDialog

    override fun initViews() = with(binding) {
        super.initViews()

        constraintLayoutToolbarBack.setOnClickListener {
            onBackPressed()
        }

        buttonWatchlist.setOnClickListener {
            viewModel.toggleWatchlist()
        }
    }

    override fun subscribe() {
        super.subscribe()

        viewModel.isLoading.collectLatestData(lifecycleScope) { result ->
            if (!result) {
                progressDialog.dismiss()
                binding.constraintLayoutMovieDetailView.visibility = View.VISIBLE
            } else {
                initLoading()
            }
        }

        viewModel.movieDetails.collectLatestData(lifecycleScope) {
            if (it != MovieItemResponse()) {
                with(binding) {
                    imageViewMovie.setImageResource(getDrawableFromString(it.imageName))
                    textViewTitle.text = it.title
                    textViewRating.text = resources.getString(R.string.rating, it.rating)
                    textViewShortDescriptionContent.text = it.description
                    textViewDetailsGenreContent.text = it.genre
                    textViewDetailsReleaseDateContent.text = it.releaseDate?.getConvertedDate()
                    buttonWatchlist.text = if (it.isOnWatchlist == true) getString(R.string.remove_from_watchlist) else getString(R.string.add_to_watchlist)
                    buttonWatchTrailer.setOnClickListener {_ ->
                        openUrlToBrowser(it.trailerUrl.toString())
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        finish()
        startActivity(
            createIntent<MainActivity>()
        )
    }

    private fun initLoading() {
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Loading ...")
        progressDialog.setCancelable(false) // blocks UI interaction
        progressDialog.show()
    }

    private fun openUrlToBrowser(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}