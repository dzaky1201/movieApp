package com.example.movie.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Credit(
    val cast: List<Cast>
)

@JsonClass(generateAdapter = true)
data class Cast(
    val id: Long,
    @Json(name = "cast_id")
    val castId: Long,
    @Json(name = "credit_id")
    val creditId: String?,
    val character: String?,
    val gender: Int?,
    val name: String,
    @Json(name = "profile_path")
    val profilePath: String?
)