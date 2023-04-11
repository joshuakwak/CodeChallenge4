package com.heroapps.codechallenge4.data.source.remote.response

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
data class MovieItemResponse(
    @Json(name = "id") var id: Int? = null,
    @Json(name = "title") var title: String? = null,
    @Json(name = "description") var description: String? = null,
    @Json(name = "rating") var rating: String? = null,
    @Json(name = "duration") var duration: String? = null,
    @Json(name = "genre") var genre: String? = null,
    @Json(name = "releaseDate") var releaseDate: String? = null,
    @Json(name = "trailerUrl") var trailerUrl: String? = null,
    @Json(name = "imageName") var imageName: String? = null,
    @Json(name = "isOnWatchlist") var isOnWatchlist: Boolean? = null
) : Parcelable