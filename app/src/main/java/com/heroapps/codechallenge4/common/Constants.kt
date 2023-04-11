package com.heroapps.codechallenge4.common

import com.heroapps.codechallenge4.BuildConfig

object Constants {

    const val API_ENDPOINT = "macros/s/${BuildConfig.APP_SCRIPT_DEPLOYMENT_ID}/exec"

    const val METHOD_NAME = "methodName"

    const val METHOD_GET_MOVIES = "getMovies"
    const val METHOD_GET_MOVIE_BY_ID = "getMovieById"
    const val METHOD_TOGGLE_WATCHLIST = "toggleWatchlist"

}