package com.heroapps.codechallenge4.presentation.ui.main

import android.app.AlertDialog
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.heroapps.codechallenge4.common.base.BaseActivity
import androidx.activity.viewModels
import androidx.core.view.marginStart
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.heroapps.codechallenge4.R
import com.heroapps.codechallenge4.common.util.collectLatestData
import com.heroapps.codechallenge4.common.util.createIntent
import com.heroapps.codechallenge4.common.util.showToastLong
import com.heroapps.codechallenge4.data.source.remote.response.MovieItemResponse
import com.heroapps.codechallenge4.data.source.remote.response.MoviesResponse
import com.heroapps.codechallenge4.databinding.ActivityMainBinding
import com.heroapps.codechallenge4.presentation.ui.movie.MovieDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(), MovieAdapter.MovieAdapterListener {
    override val layoutId = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()

    private lateinit var adapter: MovieAdapter

    override fun initViews() {
        super.initViews()

        binding.buttonSort.setOnClickListener {
            showSortDialog()
        }
    }

    override fun subscribe() {
        super.subscribe()

        viewModel.movieList.collectLatestData(lifecycleScope) {
            if (it.items.isNotEmpty()) {
                initAdapter(it)
            }
        }

        viewModel.isLoading.collectLatestData(lifecycleScope) { result ->
            if (!result) {
                binding.progressBar.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.VISIBLE
            }
        }
    }

    override fun onItemClick(el: MovieItemResponse) {
        startActivity(
            createIntent<MovieDetailsActivity>().apply {
                putExtra("id",el.id)
            }
        )
    }

    private fun initAdapter(m: MoviesResponse) {
        adapter = MovieAdapter(this, m, this)
        binding.recyclerView.adapter = adapter
    }

    private fun showSortDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Select sort option")

        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL

        val buttonSortTitle = Button(this)
        buttonSortTitle.text = getString(R.string.sort_title)
        linearLayout.addView(buttonSortTitle)

        val buttonSortDate = Button(this)
        buttonSortDate.text = getString(R.string.sort_date)
        linearLayout.addView(buttonSortDate)

        builder.setView(linearLayout)

        val alertDialog = builder.create()
        alertDialog.show()

        buttonSortTitle.setOnClickListener {
            initAdapter(viewModel.sortMoviesByTitle())
            alertDialog.dismiss()
        }

        buttonSortDate.setOnClickListener {
            initAdapter(viewModel.sortMoviesByDate())
            alertDialog.dismiss()
        }
    }
}