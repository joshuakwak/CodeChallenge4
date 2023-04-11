package com.heroapps.codechallenge4.data.source.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class MoviesResponse (
    @Json(name = "items")
    var items : List<MovieItemResponse> = listOf()
) : Parcelable