package com.marcocarrizales.githubreposapp.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


data class ResultsApiModel(
    @Json(name = "total_count") val totalCount: Int,
    @Json(name = "incomplete_results") val incompleteResults: Boolean,
    val items: List<RepoApiModel>,
)